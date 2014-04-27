// (c) Open Knowledge Foundation 2012. Dedicated to the public domain. Please
// use and reuse freely - you don't even need to credit (though a link back to
// ReclineJS.com is always appreciated)!


// ## Our main loop - on document ready 
jQuery(function($) {
  var $el = $('.search-here');

  // ### Overview
  //
  // We have a slightly more complex setup than is needed to allow for using
  // this demo with different backends
  //
  // There are 2 alternatives: more complex and a simpler one
  // 
  // If you just want to see how this work skip to the simple case ...

  // ### 1. More complex - use data from a backend configured in query string

  // Check for config from url query string
  var config = recline.View.parseQueryString(decodeURIComponent(window.location.search));
  if (config.backend) {
    // If we had it hand off to our more complex example setup
    setupMoreComplexExample(config);
    return;
  }

  // #### Custom template
  // 
  // Create a custom template for rendering the records
  var template = ' \
    <div class="record"> \
      <h3> \
        {{title}} <em>by {{Author}}</em> \
      </h3> \
      <p>{{description}}</p> \
      <p><code>${{price}}</code></p> \
    </div> \
  ';

  // #### Set up the search View (using custom template)
  var searchView = new SearchView({
    el: $el,
    model: dataset,
    template: template 
  });
  searchView.render();

  // #### Finally - now do the first query
  //
  // After this point the Search View will take over handling queries!
  dataset.query();
});


// ## Simple Search View
//
// This is a simple bespoke Backbone view for the Search. It Pulls together
// various Recline UI components and the central Dataset and Query (state)
// object
//
// It also provides simple support for customization e.g. of template for list of results
// 
//      var view = new SearchView({
//        el: $('some-element'),
//        model: dataset
//        // EITHER a mustache template (passed a JSON version of recline.Model.Record
//        // OR a function which receives a record in JSON form and returns html
//        template: mustache-template-or-function
//      });
var SearchView = Backbone.View.extend({
  initialize: function(options) {
    this.el = $(this.el);
    _.bindAll(this, 'render');
    this.recordTemplate = options.template;
    // Every time we do a search the recline.Dataset.records Backbone
    // collection will get reset. We want to re-render each time!
    this.model.records.bind('reset', this.render);
    this.templateResults = options.template;
  },

  // overall template for this view
  template: ' \
    <div class="controls"> \
      <div class="query-here"></div> \
    </div> \
    <div class="total"><h2><span></span> records found</h2></div> \
    <div class="body"> \
      <div class="sidebar"></div> \
      <div class="results"> \
        {{{results}}} \
      </div> \
    </div> \
    <div class="pager-here"></div> \
  ',
 
  // render the view
  render: function() {
    var results = '';
    if (_.isFunction(this.templateResults)) {
      var results = _.map(this.model.records.toJSON(), this.templateResults).join('\n');
    } else {
      // templateResults is just for one result ...
      var tmpl = '{{#records}}' + this.templateResults + '{{/records}}'; 
      var results = Mustache.render(tmpl, {
        records: this.model.records.toJSON()
      });
    }
    var html = Mustache.render(this.template, {
      results: results
    });
    this.el.html(html);

    // Set the total records found info
    this.el.find('.total span').text(this.model.recordCount);

    // ### Now setup all the extra mini-widgets
    // 
    // Facets, Pager, QueryEditor etc

    var view = new recline.View.FacetViewer({
      model: this.model
    });
    view.render();
    this.el.find('.sidebar').append(view.el);

    var pager = new recline.View.Pager({
      model: this.model
    });
    this.el.find('.pager-here').append(pager.el);

    var queryEditor = new recline.View.QueryEditor({
      model: this.model.queryState
    });
    this.el.find('.query-here').append(queryEditor.el);
  }
});

// --------------------------------------------------------
// ## Custom code very specific to this demo

// e.g. to provide custom templates for the google doc and openspending examples


// ### Handle case where we get data from a specific backend
// 
// Includes providing custom templates
function setupMoreComplexExample(config) {
  var $el = $('.search-here');
  var dataset = new recline.Model.Dataset(config);
  // async as may be fetching remote
  dataset.fetch().done(function() {
    var template = templates[dataset.get('url')] || templates['generic'];
    var searchView = new SearchView({
      el: $el,
      model: dataset,
      template: template 
    });
    searchView.render();

    dataset.queryState.set({
        size: 20,
        sort: 'city'
      },
      {silent: true}
    );
    if (dataset.get('url') in templates) {
      dataset.queryState.addFacet('city');
      dataset.queryState.addFacet('county');      
    }
    dataset.query();
  });
};

var templates = {
  // generic template function
  'generic': function(record) {
    var template = '<div class="record"> \
      <ul> \
       {{#data}} \
       <li>{{key}}: {{value}}</li> \
       {{/data}} \
     </ul> \
    </div> \
    ';
    var data = _.map(_.keys(record), function(key) {
      return { key: key, value: record[key] };
    });
    return Mustache.render(template, {
      data: data
    });
  },
  'http://localhost:9200': function(record) {
    var template = '<div class="record"> \
      <h3> \
        {{record.name}}  \
      </h3> \
      <h4> \
        <strong>{{record.city}}</strong>  \
      </h4> \
      </br> \
      <ul> \
       {{#ratings}} \
         <li>{{key}} &ndash; {{value}}</li> \
       {{/ratings}} \
       </ul> \
    </div> \
    ';
    var ratings = [];
    _.each(record.tierAndRating, function(rating) {
      ratings.push({ key: rating.tierRating, value: rating.subTier });
    });
    return Mustache.render(template, {
      record: record,
      ratings: ratings
    });
  },
  
}
