var x = require('casper').selectXPath;
var casper = require('casper').create({   
    verbose: true, 
    logLevel: 'debug',
    pageSettings: {
         // loadImages:  false,         // The WebPage instance used by Casper will
         // loadPlugins: false,         // use these settings
         userAgent: 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.94 Safari/537.4'
    }
});
casper.options.viewportSize = {width: 1280, height: 1024};

// print out all the messages in the headless browser context
casper.on('remote.message', function(msg) {
    this.echo('remote message caught: ' + msg);
});

// print out all the messages in the headless browser context
casper.on("page.error", function(msg, trace) {
    this.echo("Page Error: " + msg, "ERROR");
});


//Carrega a pagina de login
casper.start('http://localhost:8080/quiron/login.faces', function() {
   console.log("page loaded");
    if (this.exists('form#j_id6')) {
        this.echo('the form exists');
    }
});

//coloca dados no formulario de login
casper.thenEvaluate(function(){
    var login_form = document.getElementById("j_id6");


      login_form.elements["j_id6:login"].value="admin";
      login_form.elements["j_id6:senha"].value="123";

});


//tira uma print da tela de login preenchida
casper.then(function(){

    this.capture('casper_js/quiron1.jpg', undefined, {
        format: 'jpg',
        quality: 75
    });
});

//clica em ok, para fazer login
casper.thenClick(x('//*[@name="j_id6:j_id16"]'), function(){
 //não faz nada nesse momento.
});

//tira um print da tela de home, logo após o login ser feito.
casper.then(function(){
    this.wait(2000);
    this.capture('casper_js/quiron2.jpg', 
      {
        top: 0,
        left:0,
        width: 1280,
        height: 1024

      }, 
      {
        format: 'jpg',
        quality: 75,
    });
});


// FOI FEITO O LOGIN, FAZER REGRA A PARTIR DAQUI ///
casper.thenOpen('http://localhost:8080/quiron/parametro/list.faces', function() {
    this.wait(1000);
    this.capture('casper_js/quiron3.jpg', 
      {
        top: 0,
        left:0,
        width: 1280,
        height: 1024

      }, 
      {
        format: 'jpg',
        quality: 75,
    });
});


casper.run();