
//global variables
var _xmlFile = "", _doc = "", _appName = "", _tsName = "";

function getAttr(node, name) {
	// returns attribute value.
	var attrVal="";
	try {		
	      if(null != node.attributes.getNamedItem(name)) {
				attrVal = node.attributes.getNamedItem(name).nodeValue;
		   }
		return attrVal;
	} catch (e){
		alert(e.message);
		return "";
	}
}

function CreateXMLObject() {
	// Creates an XML object
	var xmlDoc = null;
	
 	var moz = (typeof document.implementation != 'undefined') && (typeof document.implementation.createDocument != 'undefined');
 	var ie = (typeof window.ActiveXObject != 'undefined');  
 
	 if(ie)
		//Internet Explorer
		xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
	else if(moz)
		//Mozilla
		xmlDoc = document.implementation.createDocument("","",null);

	return xmlDoc;
}

function ParseQueryString() {
	this.qsParm = new Array();

	var query = window.location.search.substring(1);
	//special handling for Mozilla browsers...
	var moz = (typeof document.implementation != 'undefined') && (typeof document.implementation.createDocument != 'undefined');
	if(moz)
		query = decodeURIComponent(query);

	var parms = query.split('&');

	for (var i=0; i<parms.length; i++) {
		var pos = parms[i].indexOf('=');
		if (pos > 0) {
			var key = parms[i].substring(0,pos);
			val = parms[i].substring(pos+1);
			this.qsParm[key] = val;
		}
	}

	this.getParamValue = function(param) { return this.qsParm[param]; }
}


// function replyscreenshotdiscrption(xmlFile,appName,tsName,browser,baseurl,xmlDoc)
// {

			// var xmlDoc = null;
			// xmlDoc = CreateXMLObject();	
			// var strImages= "";
			// var the_doc = xmlDoc;
			// the_doc.async=false;
			// the_doc.load(xmlFile);	

			// var cnt = 0;
			// var testSuites = the_doc.getElementsByTagName("TestSuite");

			// for(var i = 0; i < testSuites.length; i++) {
				// var testSuite = testSuites[i];
				// var _suiteName = getAttr(testSuite, "Name");		
				// suiteName = parseSuiteName(_suiteName);
				// var suiteBrowser = getAttr(testSuite, "browser");
				// var suiteUrl = getAttr(testSuite, "BaseUrl");		

				// if( _suiteName.toLowerCase() != appName.toLowerCase()||
				
					// (browser.toLowerCase() != suiteBrowser.toLowerCase())||
				
					// (baseurl.toLowerCase() != suiteUrl.toLowerCase())){
					// continue;
				// }

				// var tests = testSuite.getElementsByTagName("Test");
				// for(var v = 0; v < tests.length; v++) {
					// var test= tests[v];
					// var testName = getAttr(test, "id");
					
								
			// var busId = getAttr(test, "id"); //business id
			// //var totalExecutionTime = getAttr(test, "executionTime");	
			
			// if(busId.toLowerCase() != scenarioId.toLowerCase()){
				// continue;
			// }
					
					// if( testName.toLowerCase() != tsName.toLowerCase()){
						// continue;
					// }
				
					// var allTestCases = test.getElementsByTagName("TestCaseDetails");
					// var testCases  = allTestCases[0].getElementsByTagName("TestCase");
					// // var testCaseDescriptionTag = test.getElementsByTagName("Description");
					 // //testCaseDescription = getNodeValue(testCaseDescriptionTag);
					
					// //for(var j=0; j <testCases.length; j++){
						// var testCase = null;
						// testCase = testCases[0];
						// var testCaseName = getAttr(testCase, "id");	
						
						// //if(testCaseName.indexOf("RS") == 0){
							// //continue;
						// //}

						// var tcSortId = getAttr(testCase, "sortId");
						// var testCaseDesc = getAttr(testCase, "description");

						// var allSteps = test.getElementsByTagName("Steps");
						// var steps = allSteps[0].getElementsByTagName("Step");
						// var _image="";
							
						// for(var z = 0; z < steps.length; z++ ) {
							// step = null;
							// step = steps[z];
							// var stepSortId = getAttr(step, "sortId");
							// var id = getAttr(step, "id");
							// // alert("id"+id);
							 

							// // if(stepSortId.toLowerCase() != tcSortId.toLowerCase()){
							// // continue;
							// // }
							// //if(tcSortId == stepSortId){							
								// var image = getAttr(step, "image");
								// var result = getAttr(step, "result");
								
								// if(image.length > 0){	
												
									// image = "." + image;						 
									// _image = image;
									// //var description = "<h3>" + testCaseName + "</h3>" + testCaseDesc;	
									// //var description = testCaseName + ": " + testCaseDesc;
									// if(result == "PASS")
									  // var description = "<span style='color:green' >"+testCaseName + ":"
									  // +testCaseDesc+"</span>";
									// else
									  // var description = "<span style='color:red' alt='fail'>"+testCaseName + ":"+testCaseDesc+"</span>";
									// //alert("description len="+description.length + "desc="+description);
									// var imgPath = "";										
									// if(cnt == 0)
									// {										
										// strImages = "<p color=green><a id='replay' class=\"ssGallery\"  href=\"../"+ image +"\" title=\" " + description + "\"></a></p>";										
									// }
									// else
									// {
										// imgPath = "<p color=green><a class=\"ssGallery\"  href=\"../"+ image +"\" title=\" " + description + "\"></a></p>";
									// }
									
									// strImages = strImages + imgPath ;		
									// cnt++;
									// image="";
								// }					
															
							// //}				
							
						// } //efo steps	
                       			
						 
					// //} //eof testCases			
				// } //eof test	
			// } //eof testsuite		
		// return strImages;		
// }

function replyscreenshotdiscrption(xmlFile,appName,tsName,browser,baseurl,xmlDoc)
{

			var xmlDoc = null;
			xmlDoc = CreateXMLObject();	
			var strImages= "";
			var the_doc = xmlDoc;
			the_doc.async=false;
			the_doc.load(xmlFile);	

			var cnt = 0;
			var testSuites = the_doc.getElementsByTagName("TestSuite");

			for(var i = 0; i < testSuites.length; i++) {
				var testSuite = testSuites[i];
				var _suiteName = getAttr(testSuite, "Name");		
				suiteName = parseSuiteName(_suiteName);
				var suiteBrowser = getAttr(testSuite, "browser");
				var suiteUrl = getAttr(testSuite, "BaseUrl");		

				if( _suiteName.toLowerCase() != appName.toLowerCase()||
				
					(browser.toLowerCase() != suiteBrowser.toLowerCase())||
				
					(baseurl.toLowerCase() != suiteUrl.toLowerCase())){
					continue;
				}

				var tests = testSuite.getElementsByTagName("Test");
				
				for(var v = 0; v < tests.length; v++) {
					var test= tests[v];
					var testName = getAttr(test, "id");
								
			         var busId = getAttr(test, "id"); //business id
			//var totalExecutionTime = getAttr(test, "executionTime");	
			
			// if(busId.toLowerCase() != scenarioId.toLowerCase()){
				// continue;
			// }
					
					if( testName.toLowerCase() != tsName.toLowerCase()){
						continue;
					}
				
					var allTestCases = test.getElementsByTagName("TestCaseDetails");
					var testCases  = allTestCases[0].getElementsByTagName("TestCase");
					// var testCaseDescriptionTag = test.getElementsByTagName("Description");
					 //testCaseDescription = getNodeValue(testCaseDescriptionTag);
					var testCaseDesc="";
					var TestcaseDescArr=new Array();
					var testCaseName_idarr=new Array();
					for(var j=0; j <testCases.length; j++){
							var testCase = null;
							testCase = testCases[j];
							var testCaseName = getAttr(testCase, "id");	
							var tcSortId = getAttr(testCase, "sortId");
							var reusable = getAttr(testCase, "reusable");																	
							var testCaseDesc1=testCaseDesc;
							testCaseDesc = getAttr(testCase, "description");	
							TestcaseDescArr.push(testCaseDesc);
							testCaseName_idarr.push(testCaseName);

						} //eof testCases	
						var allSteps = test.getElementsByTagName("Steps");
						var steps = allSteps[0].getElementsByTagName("Step");
						var _image="";
						for(var z = 0; z < steps.length; z++ ) {
							step = null;
							step = steps[z];
							var stepSortId = getAttr(step, "sortId");
							var id = getAttr(step, "id");
							// alert("id"+id);						
						var reusable1 = getAttr(step, "reusable"); //test case reusable						
						// if( testCaseName.toLowerCase() != id.toLowerCase()||						
							// (tcSortId.toLowerCase() != stepSortId.toLowerCase())||						
							// (reusable.toLowerCase() != reusable1.toLowerCase())){
							// continue;
						// }
                           for(var q=0; q<testCaseName_idarr.length-1; q++){

								if (testCaseName_idarr[q]==id)
								{
								testCaseDesc=TestcaseDescArr[q];
								testCaseName=testCaseName_idarr[q];
								 break;
								}
							}

                        
								
								var image = getAttr(step, "image");
								var result = getAttr(step, "result");
								
								if(image.length > 0){	
												
									image = "." + image;						 
									_image = image;
									//var description = "<h3>" + testCaseName + "</h3>" + testCaseDesc;	
									//var description = testCaseName + ": " + testCaseDesc;
									if((result == "PASS")||(result == "INFO"))
									  var description = "<span style='color:green' >"+testCaseName + ":"
									  +testCaseDesc+"</span>";
									else
									  var description = "<span style='color:red' alt='fail'>"+testCaseName + ":"+testCaseDesc+"</span>";
									//alert("description len="+description.length + "desc="+description);
									var imgPath = "";										
									if(cnt == 0)
									{										
										strImages = "<p color=green><a id='replay' class=\"ssGallery\"  href=\"../"+ image +"\" title=\" " + description + "\"></a></p>";										
									}
									else
									{
										imgPath = "<p color=green><a class=\"ssGallery\"  href=\"../"+ image +"\" title=\" " + description + "\"></a></p>";
									}
									
									strImages = strImages + imgPath ;		
									cnt++;
									image="";
								}					
															
							//}				
							
						} //efo steps	
                       			
						 
							
				} //eof test	
			} //eof testsuite		
		return strImages;		
}

function parseSuiteName(suiteName){
	var appName = suiteName;

	if(suiteName.indexOf('^') > 0){
		var suiteNameArr = suiteName.split('^');
		appName = suiteNameArr[0];
	}
	return appName;
}

function loadFirstSlide()
{
	var replayUrl = "../replayshortcuts/SlideshowStartButtonPage.html?xml=" + _xmlFile +
			"&appName=" + _appName + "&test=" +_tsName;
	
	location.href = replayUrl;
}

function closeWin()
{
	window.close();
}