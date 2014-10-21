var page = new WebPage(), testindex = 0, loadInProgress = false;

page.onConsoleMessage = function(msg) {
  console.log(msg);
};

page.onLoadStarted = function() {
  loadInProgress = true;
  console.log("load started");
};

page.onLoadFinished = function() {
  loadInProgress = false;
  console.log("load finished");
};

var steps = [
  function() {
    //Load Login Page
    page.open("http://localhost:8080/quiron/login.faces");
  },
  function() {
    //Enter Credentials

    page.evaluate(function() {

      var login_form = document.getElementById("j_id6");

	    if (login_form.getAttribute('method') == "post") {

	      login_form.elements["j_id6:login"].value="admin";
	      login_form.elements["j_id6:senha"].value="123a";

	      return;
	    }
      
    });

  }, 
  function() {
    //Login
    page.evaluate(function() {
      var login_form = document.getElementById("j_id6");

	    if (login_form.getAttribute('method') == "post") {
	      var bt_ok = login_form.elements["j_id6:j_id16"];
	      // loadInProgress = true;
          // bt_ok.click();
          login_form.submit();
          return;
        }

    });
  }, 
  // function() {
  //   // Output content of page to stdout after form has been submitted
  //   page.open("http://localhost:8080/quiron/home.faces");
  // }
  // ,
  function() {
    // Output content of page to stdout after form has been submitted
    page.evaluate(function() {

      // console.log(document.querySelectorAll('html')[0].outerHTML);

    });
  }
];


interval = setInterval(function() {
  if (!loadInProgress && typeof steps[testindex] == "function") {
	page.render('quiron_'+(testindex)+'.png');
    console.log("step " + (testindex + 1));
    steps[testindex]();
    testindex++;
  }
  if (typeof steps[testindex] != "function") {
    console.log("test complete!");
	page.render('quiron_'+(testindex+1)+'.png');
    phantom.exit();
  }
}, 50);