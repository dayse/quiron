var x = require('casper').selectXPath;
var casper = require('casper').create({   
    verbose: true, 
    logLevel: 'debug',
    pageSettings: {
         loadImages:  false,         // The WebPage instance used by Casper will
         loadPlugins: false,         // use these settings
         userAgent: 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.94 Safari/537.4'
    }
});

// print out all the messages in the headless browser context
casper.on('remote.message', function(msg) {
    this.echo('remote message caught: ' + msg);
});

// print out all the messages in the headless browser context
casper.on("page.error", function(msg, trace) {
    this.echo("Page Error: " + msg, "ERROR");
});

var url = 'http://localhost:8080/quiron/login.faces';

casper.start(url, function() {
   console.log("page loaded");
    if (this.exists('form#j_id6')) {
        this.echo('the form exists');
       // this.fill('form#j_id6', { 
       //      'j_id6:login': 'admin', 
       //      'j_id6:senha':  '123'
       //  }, true);

       // this.fillSelectors('form#j_id6', { 

       //          'input[name="j_id6:login"]' : 'admin',
       //          'input[name="j_id6:senha"]' : '123'
       //  }, true);

    }
   // this.exists('form#j_id6');
});

casper.thenEvaluate(function(){
   // console.log("Page Title " + document.title);
   // console.log("Your name is " + document.querySelector('.headerTinymanName').textContent ); 

    var login_form = document.getElementById("j_id6");


      login_form.elements["j_id6:login"].value="admin";
      login_form.elements["j_id6:senha"].value="123";

});


casper.then(function(){

    this.capture('quiron1.jpg', undefined, {
        format: 'jpg',
        quality: 75
    });
   // console.log("Page Title " + document.title);
   // console.log("Your name is " + document.querySelector('.headerTinymanName').textContent ); 
});

casper.thenClick(x('//*[@name="j_id6:j_id16"]'), function(){

   console.log("clicked button");
});

casper.then(function(){

    this.capture('quiron2.jpg', undefined, {
        format: 'jpg',
        quality: 75
    });
});

casper.run();