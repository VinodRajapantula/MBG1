/*
 *Created-----01-12-10
 * History of updates
 * Updated on ---- 10-July-2012
 * Updated on ---- 05-July-2012
 * Last updated-----01-12-10 
 * 
 * 
 */

var PASS = "Pass";
var FAIL = "Fail";
var INFO="info"
var SKIP = "skip";
var TestErrorSuiteHTML="./templates/ErrorSuite.html";

var TestErrorSuiteLinkHTML="./templates/ErrorSuiteLink.html";
var TestsuiteHTML = "./templates/Testsuite.html";
var TestcaseHTML = "Testcase.html";
var TeststepHTML = "TestStep.html";
var TestSpellErorHTML="ErrorList.html";
var TestSpellErorLinkHTML="ErrorList_link.html";
var XML_PATH = "./xml/";
var XML_PATHTTEMP="./templates/";

var DEFAULT_BACKGROUND_COLOR = "#ffffff";
var PASS_BACKGROUND_COLOR = "#00FF00";
var FAIL_BACKGROUND_COLOR = "#FF0000";
var requiredArray=new Array();
var SuiteAllNameArr=new Array();
var setarray1=new Array();
var BrowserArray1=new Array();
var FinalTestSetData1= new Array();
var FinalSetPass1=new Array();
var FinalSetFail1=new Array();
var FinalBrowserPass1=new Array();
var FinalBrowserFail1=new Array();
var BrowserAllNameArr=new Array();

	var DatecumPassCount = 0;
	var DatecumFailCount = 0;
	var ExeDateOrTime="";
	var Datatext="";
	var glbDate="";
	

function TableRow() {
	// creates a table object
	this.tbl = document.getElementById('tblSample');
	this.position = 0;
	this.row;
	this.data;
	this.failColor = "#FF0000";
	this.passColor = "#1E762D";

	this.createLastRow = function()
				{
					var lastRow = this.tbl.rows.length; 
					var row = this.tbl.insertRow(lastRow);
				 	this.row = row;
				}

	this.insertRecord = function(data)
				{
					var one = this.row.insertCell(this.position);
					one.appendChild(data);
					this.position++;
				}

	this.applyColor = function(color)
				{
					if(color.toLowerCase() == "pass"){
						this.row.className = 'pass';
					}
					else if(color.toLowerCase() == "fail"){
						this.row.className = 'fail';
					}
					else if(color.toLowerCase() == "info"){
						this.row.className = 'info';
					}	

				}
}

function hrefTag(the_name) {
	// Constructs <a > tag
	var aTag = document.createElement('a');

	aTag.name = 'txtRow';
	aTag.id = 'txtRow';
	aTag.href= "apps.html?apps=" + the_name.toLowerCase();
	aTag.appendChild(document.createTextNode(the_name));

	return aTag;
}

function imageTag(the_name, icon_name) {
	// Constructs <a > tag
	var aTag = document.createElement('a');
    var imgPath = "";
	
	if(icon_name == "screenshot_icon.gif")
	{
		imgPath = "../images/" + icon_name;
		aTag.href=the_name.replace(".","../..");
	}
	else if(icon_name == "replay_icon_PNG.png")
	{
		imgPath = "../../testReports/images/" + icon_name;
		aTag.href=the_name.replace(".","..");
	}
	else
	{
		imgPath = "../testReports/images/" + icon_name;
		aTag.href=the_name.replace(".","..");
	}

	aTag.name = 'txtRow';
	aTag.id = 'txtRow';
	//aTag.href=the_name.replace(".","../..");     
	aTag.target="_blank";

	if("" != the_name && the_name.length > 0){		
		aTag.appendChild(createImageTag(imgPath));
	}
  
	return aTag;
}

function createImageTag(imgPath){

var oImg = document.createElement("img");
oImg.setAttribute('src', imgPath);
oImg.setAttribute('border', '0px');

return oImg;

}


function textTag(data) {
	// constructs normal text
	return document.createTextNode(data);
}

function textDescTag(data) {
	var elem = document.createElement("div");
	var dataArr = data.split(/\r?\n/);

	for(var i=0; i<dataArr.length; i++){
		var _text = document.createTextNode(dataArr[i]);
		elem.appendChild(_text);
		var lineBreak = document.createElement("br");
		elem.appendChild(lineBreak);
	}
		
	return elem; 
}

function textDescTag1(data) {
	var elem = document.createElement("div");
	var dataArr = data.split(/_/);

	for(var i=0; i<dataArr.length; i++){
		var _text = document.createTextNode(dataArr[i]);
		elem.appendChild(_text);
		var lineBreak = document.createElement("br");
		elem.appendChild(lineBreak);
	}
		
	return elem; 
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

function getAttr(node, name) {
	// returns attribute value.
	var attrVal="";
	try {		
	      if(null != node.attributes.getNamedItem(name)) {
				attrVal = node.attributes.getNamedItem(name).nodeValue;
		   }
		  // alert(name+' = '+attrVal);
		return attrVal;
	} catch (e){
		//alert("rama");
		alert(e.message);
		return "";
	}
}

function getNodeValue(node) {
	// returns attribute value.
	try {
		//alert(node.firstChild.nodeValue);
	        return node.firstChild.nodeValue;
	} catch (e){
		//alert("rama");
		//alert(e.message);
		return "";
	}
}

function parseSuiteLevelXML(xmlFile, doc) {
	var the_doc = doc;
	the_doc.async=false;
	the_doc.load(xmlFile);
	

	var testResults = the_doc.getElementsByTagName("Environment");

	var environmentDetails = testResults[0];
	//var category = getAttr(environmentDetails, "category");
	var oS = getAttr(environmentDetails, "os");
	var hostname = getAttr(environmentDetails, "hostname");
	var overallStartTime = getAttr(environmentDetails, "startTime");
	var overallEndTime = getAttr(environmentDetails, "endTime");
	var overallExecutionTime = getAttr(environmentDetails, "totalExecutionDuration");
		
	var logFile = getAttr(environmentDetails, "logFile");
  	logFile = logFile.replace("//", "/");
	

	document.getElementById('os_info').innerHTML = oS;
	document.getElementById('host_info').innerHTML = hostname;
	document.getElementById("start_time").innerHTML = overallStartTime;
	document.getElementById("end_time").innerHTML = overallEndTime;
	document.getElementById("total_execution_duration").innerHTML = overallExecutionTime;


	testSuites = the_doc.getElementsByTagName("TestSuite");
	
	var cumPassCount = 0;
	var cumFailCount = 0;
	var cumTotalCount = 0;
	for(var i = 0; i < testSuites.length; i++) {
	
		var passCount = 0;
		var failCount = 0;
		var totalCount = 0;
		var testSuite = testSuites[i];
		
		var _suiteName = getAttr(testSuite, "Name");	
		suiteName = parseSuiteName(_suiteName);
		var category = getAttr(testSuite, "Category");	
		var executionEngine = getAttr(testSuite, "ExecutionEngine");	
		var totalExecutionTime = getAttr(testSuite, "executionTime");
		//alert(totalExecutionTime);
		var browser = getAttr(testSuite, "browser");
		var BaseUrl = getAttr(testSuite, "BaseUrl");
		var tests = testSuite.getElementsByTagName("Test");
				
		if(tests.length > 0 ) {
			document.getElementById("no_files_found").style.display='none';
		}

		for(var v = 0; v < tests.length; v++) {
			//alert(tests.length);
			
			var test= tests[v];
			var isPassed = PASS;
			var resultTag = test.getElementsByTagName("Result");
			var allSteps = test.getElementsByTagName("Steps");
			var steps = allSteps[0].getElementsByTagName("Step");
			
			isPassed = getNodeValue(resultTag[0]);
			if(isPassed.toLowerCase() == PASS.toLowerCase()) {
				passCount = parseInt(passCount) + 1;
			}
			else {
				failCount = parseInt(failCount) + 1;
			}

			totalCount = totalCount + 1;
		}
		cumPassCount = parseInt(cumPassCount) + parseInt(passCount);
	    cumFailCount = parseInt(cumFailCount) + parseInt(failCount);
	    cumTotalCount = parseInt(cumTotalCount) + parseInt(totalCount);
		
		var params = "apps=" + _suiteName.toLowerCase() + "&browser=" + browser + "&baseurl=" +escape(BaseUrl) + "&xml=" + "."+xmlFile;
		var allParams = params + "&resultType=all";
		var passParams = params + "&resultType=pass";
		var failParams = params + "&resultType=fail";
		var aTag = constructHrefTag(TestsuiteHTML, suiteName, allParams);

		aTag.className = 'view';
		
		if(passCount > 0){
			passCount = constructHrefTag(TestsuiteHTML, passCount, passParams);

			passCount.className = 'pass';
		}
		else {
			passCount = textTag(passCount);
		}
		
		if(failCount  > 0){
			failCount = constructHrefTag(TestsuiteHTML, failCount, failParams);
			failCount.className = 'fail';
		}
		else {
			failCount = textTag(failCount);
		}

		totalCount = constructHrefTag(TestsuiteHTML,totalCount,allParams);	

		browser=textTag(browser);
		BaseUrl=textTag(BaseUrl);
		totalExecutionTime = textTag(totalExecutionTime);
		alert(totalExecutionTime);
		category=textTag(category);
		executionEngine=textTag(executionEngine);

		var tableRow = new TableRow();
		tableRow.className='td';
		tableRow.createLastRow();
		tableRow.insertRecord(aTag);
		tableRow.insertRecord(category);
		tableRow.insertRecord(executionEngine);
		tableRow.insertRecord(browser);
		tableRow.insertRecord(BaseUrl);
		tableRow.insertRecord(passCount);
		tableRow.insertRecord(failCount);
		tableRow.insertRecord(totalCount);
		tableRow.insertRecord(totalExecutionTime);
		tableRow.insertRecord(imageTag(logFile,"log_file_icon.png"));		
	}
	document.getElementById('totPass').innerHTML = cumPassCount;
	document.getElementById('totFail').innerHTML = cumFailCount;
	document.getElementById('total').innerHTML = cumTotalCount;
//	alert("cumPassCount["+cumPassCount+"]" +" , " +"cumFailCount["+cumFailCount+"]" +","+"cumTotalCount["+cumTotalCount+"]" );
	
}




function parseAppsLevelXML(xmlFile, doc, appName, browser, baseurl, resultType) {
	

	//Environment Details added
	loadEnvironmentDetails(doc, xmlFile);
    var EroorRoot="../xml/ErrorResults.xml"		
	// alert(new File(EroorRoot).exists());
	linkflag=0;
	spellflag=0;
	resultType = resultType.toLowerCase();
    var appName = white_space(appName);
	browser = white_space(browser);
	var _appName = parseSuiteName(appName);
    var spellfilesarr=new Array();
	var linkfilearr=new Array();
	var Params_link="";
	 var Params_spell="";		
	document.getElementById("testsuitename").innerHTML = _appName.toUpperCase();
	var pieces = xmlFile.split(/[\s/]+/);
	var file1= pieces[pieces.length-1]
	var splErrFile=xmlFile.replace(file1,"SpellErrors_"+file1);	
	var linkErrFile=xmlFile.replace(file1,"LinkErrors_"+file1);
	
	var the_doc_Errorlink = CreateXMLObject();
	the_doc_Errorlink.async=false;
	the_doc_Errorlink.load(EroorRoot);
	Spellfiles = the_doc_Errorlink.getElementsByTagName("SpellError");
	linkfiles = the_doc_Errorlink.getElementsByTagName("LinkError");
	
		for(var u = 0; u < Spellfiles.length; u++)
		{
			var spellfile= Spellfiles[u];
			var _splfile = getAttr(spellfile, "file");
			if (splErrFile.search(_splfile)>0)
			{
			spellflag=1;
			break;
			}
			
			
        }			
		for(var n = 0; n < linkfiles.length; n++)
		{
			var linkfile= linkfiles[n];
			var _lnkfile = getAttr(linkfile, "file");			
			if (linkErrFile.search(_lnkfile)>0)
			{
			linkflag=1;
			break;
			}
			
        }
		
	
	var the_doc = doc;
	the_doc.async=false;
	the_doc.load(xmlFile);
	if(spellflag==1)
	{
		var the_doc_spell = CreateXMLObject();		
		the_doc_spell.async=false;
		the_doc_spell.load(splErrFile);
	}
	//var the_doc_link=doc;
	if(linkflag==1)
	{
	var the_doc_link = CreateXMLObject();
	the_doc_link.async=false;
    the_doc_link.load(linkErrFile);	
	}
	
	testSuites = the_doc.getElementsByTagName("TestSuite");
	//testSuites_spell = the_doc_spell.getElementsByTagName("TestSet");
	//testSuites_link = the_doc_link.getElementsByTagName("TestSet");


	
	for(var i = 0; i < testSuites.length; i++) {
		var testSuite = testSuites[i];
		//var testSuite_spell = testSuites_spell[i];
		//var testSuite_link = testSuites_link[i];

				//var _suiteName_spell= getAttr(testSuite_spell,"name");
				//var _suiteName_link = getAttr(testSuite_link,"name");
		
		
		var _suiteName = getAttr(testSuite, "Name");
		suiteName = parseSuiteName(_suiteName);
		var category = getAttr(testSuite, "Category");
		var executionEngine = getAttr(testSuite, "ExecutionEngine");
		var suiteBrowser = getAttr(testSuite, "browser");
		var suiteUrl = getAttr(testSuite, "BaseUrl");			

		if( (_suiteName.toLowerCase() != appName.toLowerCase()) ||
			(browser.toLowerCase() != suiteBrowser.toLowerCase()) ||
			(baseurl.toLowerCase() != suiteUrl.toLowerCase()) ){
			continue;
		}

		document.getElementById('browser_name').innerHTML = suiteBrowser;
    	document.getElementById('executionUrl').innerHTML = suiteUrl;
		document.getElementById('category').innerHTML = category;
		document.getElementById('executionEngine').innerHTML = executionEngine;
		var testReport=0;
		var tests = testSuite.getElementsByTagName("Test");
	//	var tests_spell= testSuite_spell.getElementsByTagName("TestScenario");
	//	var tests_link = testSuite_link.getElementsByTagName("TestScenario");
		var sno =0;
		var testReportCaseRequirementIdTag;
		var totalExecutionTime=0;
		var totalExecutionTimeFinal=0;
		
		var testResults = the_doc.getElementsByTagName("Environment");//chaitanya
	    var environmentDetails = testResults[0];	
	    var overallExecutionTime = getAttr(environmentDetails, "totalExecutionDuration");
		for(var v = 0; v < tests.length; v++) {
			var test= tests[v];
			if(v!=0)
				{
				testReport= tests[v-1];
				var testReportCaseRequirementIdTag =testReport.getElementsByTagName("TestCaseRequirementId");
				if(testReport.getElementsByTagName("TestCaseRequirementId").length > 0){
					testReportCaseRequirementIdTag = getNodeValue(testReportCaseRequirementIdTag[0]);
				}
				}		
			var testResults = test.getElementsByTagName("Result");
			var testResult = getNodeValue(testResults[0]).toLowerCase();
			if( (resultType != "all") && (resultType != testResult)) {
				continue;
			}

			var isPassed = PASS;
			var testName = getAttr(test, "id");
			//Logic needs to be implemented to loop through all the scenarios for overall execution
			
			//totalExecutionTime = getAttr(test, "executionTime");
			/*alert("exect time ind "+totalExecutionTime);
			 //parseInt(totalExecutionTimeFinal) + parseInt(totalExecutionTime);
			//totalExecutionTimeFinal=  parseFloat(totalExecutionTime);
			totalExecutionTimeFinal= parseFloat("00:05") + parseFloat("00:06");
			alert("final " +totalExecutionTimeFinal);
			//alert(totalExecutionTime);*/
			totalExecutionTime=overallExecutionTime;//chaitanya
			
			
			var allSteps = test.getElementsByTagName("Steps");
			var steps = allSteps[0].getElementsByTagName("Step");
			var testCaseDescriptionTag =  test.getElementsByTagName("Description");
			
			var testCaseRequirementIdTag =test.getElementsByTagName("TestCaseRequirementId");			
			testCaseDescription = getNodeValue(testCaseDescriptionTag[0]);	
		
			
			var testCaseRequirementId = "";
			if(test.getElementsByTagName("TestCaseRequirementId").length > 0){
				testCaseRequirementId = getNodeValue(testCaseRequirementIdTag[0]);
			}			

			if(testResult == FAIL.toLowerCase()) {
				isPassed = FAIL;
			}
			
			if(spellflag==1)
			{
           	
				testSuites_spell = the_doc_spell.getElementsByTagName("TestSet");														
					for(var p = 0; p < testSuites_spell.length; p++) {
						var testSuite_spell = testSuites_spell[p];
						
						var _suiteName_spell= getAttr(testSuite_spell,"name");
						var tests_spell= testSuite_spell.getElementsByTagName("TestScenario");
						for(var k = 0; k < tests_spell.length; k++) {
							var test_spell= tests_spell[k];
							var testName_spell = getAttr(test_spell, "id");
							if( (_suiteName.toLowerCase() == _suiteName_spell.toLowerCase()) && (testName_spell.toLowerCase() == testName.toLowerCase()))
							{
							  Params_spell="apps=" + appName + "&browser=" + browser + "&baseurl=" + escape(baseurl) + "&xml="+ splErrFile + "&test=" + testName +"&resultType=" + testResult + "&sno=" + sno;
							 
							}
							
						}
					}
					
					
				var Params_spell_View = "<img src=\"SpellError.png\"/>";
				path="../images/SpellError.png"
				var Params_spell_Viewtag = Params_spell_Viewtag = textTag(Params_spell_View);
				if(Params_spell!=""){
					Params_spell_Viewtag = constructHrefTagViewspell(TestSpellErorHTML, Params_spell_View, Params_spell,path);				
				//fullReportViewTag.className = isPassed.toLowerCase();
				}
			}	
			if(linkflag==1)
			{
					var Params_link="";
					testSuites_link = the_doc_link.getElementsByTagName("TestSet");
					for(var p = 0; p < testSuites_link.length; p++) {
						var testSuite_link = testSuites_link[p];
						var _suiteName_link= getAttr(testSuite_link,"name");
						var tests_link= testSuite_link.getElementsByTagName("TestScenario");
						for(var l = 0; l < tests_link.length; l++) {
							var test_link= tests_link[l];
							var testName_link = getAttr(test_link, "id");
							if( (_suiteName.toLowerCase() == _suiteName_link.toLowerCase()) && (testName_link.toLowerCase() == testName.toLowerCase()))
							{
							  Params_link="apps=" + appName + "&browser=" + browser + "&baseurl=" + escape(baseurl) + "&xml="+ linkErrFile + "&test=" + testName +"&resultType=" + testResult + "&sno=" + sno;
							 
							}
							
						}	
                    }					
				var Params_link_View = "<img src=\"linkerror.png\"/>";
				path1="../images/linkerror.png"
				var Params_link_Viewtag = Params_link_Viewtag = textTag(Params_link_View);
				if(Params_link!=""){
					Params_link_Viewtag = constructHrefTagViewspell(TestSpellErorLinkHTML, Params_link_View, Params_link,path1);				
					//fullReportViewTag.className = isPassed.toLowerCase();
				}
			 			 
			} 
			 
			var params = "";
			params = "apps=" + appName + "&browser=" + browser + "&baseurl=" + escape(baseurl) + "&xml="
					+ xmlFile + "&test=" + testName +"&resultType=" + testResult + "&sno=" + sno;
			
			// Full Report View link from Secnario Report Level Report
			var fullReportViewParams = "apps="+appName+"&browser="+browser+"&baseurl="
									+ baseurl+"&xml="+xmlFile+"&test="+ testName+"&resultType="
									+ resultType+"&testCaseId=";
						
			if(test.getElementsByTagName("TestCaseRequirementId").length == 0){
				//skipping TestCase Page for backward compatibility.
				TestcaseHTML="TestStep.html";
				params = params +"&testCaseId=";
			}
			
			var aTag = aTag = textTag(testName);
			if(steps.length > 0){
				aTag = constructHrefTag(TestcaseHTML, testName, params);
				aTag.className = isPassed.toLowerCase();
			}
			alert
			// Full Report View link
			var fullReportView = "<img src=\"images.jpg\"/>";
			var fullReportViewTag = fullReportViewTag = textTag(fullReportView);
			if(steps.length > 0){
				fullReportViewTag = constructHrefTagView(TeststepHTML, fullReportView, fullReportViewParams);				
				//fullReportViewTag.className = isPassed.toLowerCase();
			}
			
			

			//Replayscreenshots at Secnario Level link	
			//alert("hello"+xmlFile);
			//alert("xmlDoc"+xmlDoc);
			//alert("appName"+appName);
			//alert("testName"+testName);
			//alert("browser"+browser);
			//alert("baseurl"+baseurl);
			
			var replayUrl = "../replayshortcuts/Replayshortcuts.html?xml=" + xmlFile +"&appName=" + appName + "&test=" +testName + "&browser=" +browser +"&baseurl=" +baseurl;			
			
		//	var replayUrl = "../replayshortcuts/Replayshortcuts.html?xml=" + xmlFile + "&doc=" + xmlDoc+
			//	"&appName=" + appName + "&test=" +testName;

			//var replayUrl = "../replayshortcuts/SlideshowStartButtonPage.html?xml=" + xmlFile + "&doc=" + xmlDoc+
			//	"&appName=" + appName + "&test=" +testName;			

			replayScreenshotsTag = constructReplaySSHrefTag(replayUrl, "replay_icon_PNG.png");	

			var data = textTag(isPassed);
			testCaseDescription = textDescTag(testCaseDescription);			
			//aTag=textDescTag(aTag);
			totalExecutionTime = textTag(totalExecutionTime);			
			//tableRow.insertRecord(textDescTag1(testName));			
			if(testReportCaseRequirementIdTag!=testCaseRequirementId){
			//aTag=textDescTag(aTag);
			sno++;		
			var tableRow = new TableRow();
			tableRow.createLastRow();
			tableRow.applyColor(isPassed);
			tableRow.insertRecord(textTag(sno));	
			tableRow.insertRecord(aTag);
			tableRow.insertRecord(textTag(testCaseRequirementId)); 			
			tableRow.insertRecord(testCaseDescription);
			tableRow.insertRecord(data);
			tableRow.insertRecord(totalExecutionTime);
			tableRow.insertRecord(fullReportViewTag);
			tableRow.insertRecord(replayScreenshotsTag);
			
			if (Params_spell!="")
			{
			tableRow.insertRecord(Params_spell_Viewtag);
			Params_spell="";
			}
			else
			{
				tableRow.insertRecord(textTag(""));
			}
			if (Params_link!="")
			{
			tableRow.insertRecord(Params_link_Viewtag);
			Params_link="";
			}
			else
			{
				tableRow.insertRecord(textTag(""));
			}
		}
			//tableRow.insertRecord(imageTag(replayUrl,"replay_icon_PNG.png"));
		}
	}
	//load();
	//google.load("visualization", "1", {packages:["corechart"]})
    //google.setOnLoadCallback(drawChart_graph);


}

// function parseStepLevelXML(xmlFile, doc, appName, browser, baseurl, scenarioId, testCaseId) {
	// var the_doc = doc;
	// var the_testName = scenarioId;

	// //Environment Details added
	// loadEnvironmentDetails(doc, xmlFile);
	// //displaying Scenario Id to Environment Info	
	// if(testCaseId.length > 0){
		
		// the_testName = testCaseId;	
        
	// }

	

	// appName = white_space(appName);
	// browser = white_space(browser);
	// var _appName = parseSuiteName(appName);

	// document.getElementById("teststepname").innerHTML = _appName.toUpperCase();
	// document.getElementById("scenario_id").innerHTML = scenarioId;

	// testSuites = the_doc.getElementsByTagName("TestSuite");

	// for(var i = 0; i < testSuites.length; i++) {
		// var testSuite = testSuites[i];
		// var _suiteName = getAttr(testSuite, "Name");
		// suiteName = parseSuiteName(_suiteName);
		// var category = getAttr(testSuite, "Category");
		// var executionEngine = getAttr(testSuite, "ExecutionEngine");
		// var suiteBrowser = getAttr(testSuite, "browser");
		// var suiteUrl = getAttr(testSuite, "BaseUrl");

		// if( (_suiteName.toLowerCase() != appName.toLowerCase()) ||
			// (browser.toLowerCase() != suiteBrowser.toLowerCase()) ||
			// (baseurl.toLowerCase() != suiteUrl.toLowerCase()) ){
			// continue;
		// }

		// document.getElementById('browser_name').innerHTML = suiteBrowser;
    	// document.getElementById('executionUrl').innerHTML = suiteUrl;
		// document.getElementById('category').innerHTML = category;
		// document.getElementById('executionEngine').innerHTML = executionEngine;

		// var tests = testSuite.getElementsByTagName("Test");
		// for(var v = 0; v < tests.length; v++) {
			// var test= tests[v];
			// var testName = getAttr(test, "id");

			// if(testCaseId.length == 0 && testName.toLowerCase() != the_testName.toLowerCase()){
				// continue;
			// }
			
			// var allSteps = test.getElementsByTagName("Steps");
			// var steps = allSteps[0].getElementsByTagName("Step");

			// for(var z = 0; z < steps.length; z++ ) {
				// step = null;
				// step = steps[z];
				// var id = getAttr(step, "id");

 				// if(testCaseId.length > 0 && id.toLowerCase() != testCaseId.toLowerCase()){
				 // continue;
			    // }
				
				// var action = getAttr(step, "action");
				// var stepDesc = getAttr(step, "description");
				// var errorMessage = getAttr(step, "errorMessage");
				
				// var image = getAttr(step, "image");
				// var object = getAttr(step, "object");
				// var result = getAttr(step, "result");
				// var stepDescription = getAttr(step, "description");
				// var executionTime = getAttr(step, "executionTime");
				// var sortId = getAttr(step, "sortId");	

				// object = object.replace(/_/g, " ");
				// action = action.replace(/_/g, " ");

				// //Updated by Swetha on 25/04/2011
				// var objType = "";
				// var valueIndex = stepDesc.indexOf("parsed value");
				// valueIndex = valueIndex + 13;
				// var value = stepDesc.substring(valueIndex);
				// var actionString = action.toString();
				// var objectString = object.toString();				

				// if((action.toLowerCase()) == ("click".toLowerCase()))
				// {
					// if(objectString.search("btn ") == 0)
						// objType= "button ";
					// else if(objectString.search("lnk") == 0)
						// objType= "link ";
					// else if(objectString.search("spn") == 0)
						// objType= "span ";
					// stepDescription= "Clicking on the " + objType + "'" + object+ "'";
					// if (result.search(/PASS/i)==0)
					// {
						// result="INFO"
					// }
				// }
				// else if ((action.toLowerCase()) == ("open".toLowerCase()))
				// {
					// stepDescription= "Launch the Browser '" + suiteBrowser + "' with the URL " + suiteUrl+ ""
					// if (result.search(/PASS/i)==0)
					// {
						// result="INFO"
					// }
				// }
			    // else if (action.search(/close/i)==0)
				// {
					// stepDescription= "Closing the  "+value+" windiow or Window popup'" 
					// if (result.search(/PASS/i)==0)
					// {
						// result="INFO"
					// }
				// }
				 // else if (action.search(/get/i)==0)
				// {
				    // action=action.substring(3);
					// stepDescription= "Getting the "+action+" form " +object;
					// if (result.search(/PASS/i)==0)
					// {
						// result="INFO"
					// }
				// }
				// else if ((action.toLowerCase()) == ("type".toLowerCase()))
				// {
					// stepDescription= "Typing the value  " + value +"  in the Text box" ;
				// }
				// else if ((action.toLowerCase()) == ("wait".toLowerCase()))
				// {
					// stepDescription= "Waiting till " + value + " milliseconds "
				// }
				// else if ((action.toLowerCase()) == ("waitForPagetoLoad".toLowerCase()))
				// {
					// stepDescription= "Waiting for page to load";
				// }
				// else if (action.search("waitFor") == 0)
				// {
					// stepDescription= "Waiting for <<" + object+ ">>"
				// }
				// else if (action.toLowerCase() ==  ("verifyState".toLowerCase()))
				// {
					// stepDescription= "Verifying the State of the Object '" + object+ "'and the value to be " + value
				// }
				// else if ((action.toLowerCase()).search("verify") == 0)
				// {
					// actionText = action.substring("verify".length,action.length);
					// //stepDescription= "Verifying " + actionText + " for object <<" + object+ ">> to be " + value
					// if (object=="novalue")
					// {
					// }
					// else
					// {
					// stepDescription= "Verifying " + actionText + " of the object '" + object+ "' and the value to be " + value
					// }
				// }

				// var stepName = "Verifies " + action + " " + object;
				// var stepDesc = action + " " + object + " is done"; 
	            		
	            		// var tableRow = new TableRow();
				// tableRow.createLastRow();
				// tableRow.applyColor(result);
				// tableRow.insertRecord(textTag(z+1));				
				// tableRow.insertRecord(textTag(id));
				// //tableRow.insertRecord(textTag(sortId));
				// tableRow.insertRecord(textTag(stepDescription));
				// tableRow.insertRecord(textTag(result));
				// tableRow.insertRecord(textTag(errorMessage));
				// //tableRow.insertRecord(textTag(executionTime));
				// tableRow.insertRecord(imageTag(image,"screenshot_icon.gif"));
			// }
		// }
	// }
// }


function parseStepLevelXML(xmlFile, doc, appName, browser, baseurl, scenarioId, testCaseId) {
	var the_doc = doc;
	var the_testName = scenarioId;

	//Environment Details added
	loadEnvironmentDetails(doc, xmlFile);
	//displaying Scenario Id to Environment Info	
	if(testCaseId.length > 0){
		
		the_testName = testCaseId;		
	}

	
	appName = white_space(appName);
	browser = white_space(browser);
	var _appName = parseSuiteName(appName);
	if(_appName!=null){
		document.getElementById("teststepname").innerHTML = _appName.toUpperCase();
	}
	document.getElementById("scenario_id").innerHTML = scenarioId;

	testSuites = the_doc.getElementsByTagName("TestSuite");

	for(var i = 0; i < testSuites.length; i++) {
		var testSuite = testSuites[i];
		var _suiteName = getAttr(testSuite, "Name");
		suiteName = parseSuiteName(_suiteName);
		var category = getAttr(testSuite, "Category");
		var executionEngine = getAttr(testSuite, "ExecutionEngine");
		var suiteBrowser = getAttr(testSuite, "browser");
		var suiteUrl = getAttr(testSuite, "BaseUrl");

		if( (_suiteName.toLowerCase() != appName.toLowerCase()) ||
			(browser.toLowerCase() != suiteBrowser.toLowerCase()) ||
			(baseurl.toLowerCase() != suiteUrl.toLowerCase()) ){
			continue;
		}

		document.getElementById('browser_name').innerHTML = suiteBrowser;
    	document.getElementById('executionUrl').innerHTML = suiteUrl;
		document.getElementById('category').innerHTML = category;
		document.getElementById('executionEngine').innerHTML = executionEngine;

		var tests = testSuite.getElementsByTagName("Test");
		for(var v = 0; v < tests.length; v++) {
			var test= tests[v];
			var testName = getAttr(test, "id");

			if(testCaseId.length == 0 && testName.toLowerCase() != the_testName.toLowerCase()){
				continue;
			}
			
			var allSteps = test.getElementsByTagName("Steps");
			var steps = allSteps[0].getElementsByTagName("Step");
			var count =0;
			for(var z = 0; z < steps.length; z++ ) {
				step = null;
				step = steps[z];
				var id = getAttr(step, "id");

 				if(testCaseId.length > 0 && id.toLowerCase() != testCaseId.toLowerCase()){
				 continue;
			    }
				
				var action = getAttr(step, "action");
				var stepDesc = getAttr(step, "description");
				var errorMessage = getAttr(step, "errorMessage");
				
				var image = getAttr(step, "image");
				var object = getAttr(step, "object");
				var result = getAttr(step, "result");
				var stepDescription = getAttr(step, "description");
				var executionTime = getAttr(step, "executionTime");
				var sortId = getAttr(step, "sortId");	
				var stepId = getAttr(step, "stepId");
				
				
				object = object.replace(/_/g, " ");
				action = action.replace(/_/g, " ");

				//Updated by Swetha on 25/04/2011
				var objType = "";
				var valueIndex = stepDesc.indexOf("parsed value");
				valueIndex = valueIndex + 13;
				var value = stepDesc.substring(valueIndex);
				var actionString = action.toString();
				var objectString = object.toString();				

				// if((action.toLowerCase()) == ("click".toLowerCase()))
				// {
					// if(objectString.search("btn ") == 0)
						// objType= "button ";
					// else if(objectString.search("lnk") == 0)
						// objType= "link ";
					// else if(objectString.search("spn") == 0)
						// objType= "span ";
					// stepDescription= "Clicking on the " + objType + "<<" + object+ ">>";
				// }
				// else if ((action.toLowerCase()) == ("type".toLowerCase()))
				// {
					// stepDescription= "Typing the Text " + value + " in <<" + object+ " >>"
				// }
				// else if ((action.toLowerCase()) == ("wait".toLowerCase()))
				// {
					// stepDescription= "Waiting till " + value + " milliseconds "
				// }
				// else if ((action.toLowerCase()) == ("waitForPagetoLoad".toLowerCase()))
				// {
					// stepDescription= "Waiting for page to load";
				// }
				// else if (action.search("waitFor") == 0)
				// {
					// stepDescription= "Waiting for <<" + object+ ">>"
				// }
				// else if (action.toLowerCase() ==  ("verifyState".toLowerCase()))
				// {
					// stepDescription= "Verifying the State of the Object <<" + object+ ">> to be " + value
				// }
				// else if ((action.toLowerCase()).search("verify") == 0)
				// {
					// actionText = action.substring("verify".length,action.length);
					// stepDescription= "Verifying " + actionText + " for object <<" + object+ ">> to be " + value
				// }

				// var stepName = "Verifies " + action + " " + object;
				// var stepDesc = action + " " + object + " is done"; 
	            		
	            		var tableRow = new TableRow();
	           	if(result!="SKIP"){	           		
				tableRow.createLastRow();
				tableRow.applyColor(result);
				tableRow.insertRecord(textTag(id));
				count=count+1;
				tableRow.insertRecord(textTag(stepId));				
				//tableRow.insertRecord(textTag(sortId));
				tableRow.insertRecord(textTag(stepDescription));
				tableRow.insertRecord(textTag(result));
				tableRow.insertRecord(textTag(executionTime));
				tableRow.insertRecord(textTag(errorMessage));
				
				tableRow.insertRecord(imageTag(image,"screenshot_icon.gif"));
			}
			}
		}
	}
}

/**
* This function used to view the Re Usable Test Cases at Step Level
*/
// function parseReusableStepLevelXML(xmlFile, doc, appName, browser, baseurl, scenarioId, testCaseId, testCaseSortId) {
	// var the_doc = doc;
	// var the_testName = scenarioId;

	// //Environment Details added
	// loadEnvironmentDetails(doc, xmlFile);	

    // appName = white_space(appName);
	// browser = white_space(browser);
	// var _appName = parseSuiteName(appName);

	// document.getElementById("teststepname").innerHTML = _appName.toUpperCase();
	// document.getElementById("scenario_id").innerHTML = scenarioId;
	
	// testSuites = the_doc.getElementsByTagName("TestSuite");

	// for(var i = 0; i < testSuites.length; i++) {
		// var testSuite = testSuites[i];
		// var _suiteName = getAttr(testSuite, "Name");
		// suiteName = parseSuiteName(_suiteName);
		// var category = getAttr(testSuite, "Category");
		// var executionEngine = getAttr(testSuite, "ExecutionEngine");
		// var suiteBrowser = getAttr(testSuite, "browser");
		// var suiteUrl = getAttr(testSuite, "BaseUrl");

		// if( (_suiteName.toLowerCase() != appName.toLowerCase()) ||
			// (browser.toLowerCase() != suiteBrowser.toLowerCase()) ||
			// (baseurl.toLowerCase() != suiteUrl.toLowerCase()) ){
			// continue;
		// }

		// document.getElementById('browser_name').innerHTML = suiteBrowser;
    	// document.getElementById('executionUrl').innerHTML = suiteUrl;
		// document.getElementById('category').innerHTML = category;
		// document.getElementById('executionEngine').innerHTML = executionEngine;

		// var tests = testSuite.getElementsByTagName("Test");

		// for(var v = 0; v < tests.length; v++) {
			// var test= tests[v];
			// var testName = getAttr(test, "id");

			// if(testName.toLowerCase() != scenarioId.toLowerCase()){
				// continue;
			// }			

			// var allSteps = test.getElementsByTagName("Steps");
			// var steps = allSteps[0].getElementsByTagName("Step");
			// var arrLen=1;			
			// var sNoArray = new Array();
			// var arrCount = 0;


		   // for(var z = 0; z < steps.length; z++ ) {
			// step = null;
			// step = steps[z];
			// var id = getAttr(step, "id");
			// var sortId = getAttr(step, "sortId");

			// if(sortId != testCaseSortId){						
				// continue;
			// }	

			// var action = getAttr(step, "action");
			// var object = getAttr(step, "object");
			// var stepDesc = getAttr(step, "description");
			// var errorMessage = getAttr(step, "errorMessage");	
			// var image = getAttr(step, "image");				
			// var result = getAttr(step, "result");
			// var stepDescription = getAttr(step, "description");	
			// var executionTime = getAttr(step, "executionTime");

			// // ******* Reformating the Desrciption Starts *******
			// object = object.replace(/_/g, " ");
			// action = action.replace(/_/g, " ");

			// var objType = "";
			// var valueIndex = stepDesc.indexOf("parsed value");
			// valueIndex = valueIndex + 13;
			// var value = stepDesc.substring(valueIndex);
			// var actionString = action.toString();
			// var objectString = object.toString();				

			// if((action.toLowerCase()) == ("click".toLowerCase()))
			// {
				// if(objectString.search("btn ") == 0)
					// objType= "button ";
				// else if(objectString.search("lnk") == 0)
					// objType= "link ";
				// else if(objectString.search("spn") == 0)
					// objType= "span ";
				// stepDescription= "Clicking on the " + objType + "<<" + object+ ">>";
			// }
			// else if ((action.toLowerCase()) == ("type".toLowerCase()))
			// {
				// stepDescription= "Typing the Text " + value + " in <<" + object+ " >>"
			// }
			// else if ((action.toLowerCase()) == ("wait".toLowerCase()))
			// {
				// stepDescription= "Waiting till " + value + " milliseconds "
			// }
			// else if ((action.toLowerCase()) == ("waitForPagetoLoad".toLowerCase()))
			// {
				// stepDescription= "Waiting for page to load";
			// }
			// else if (action.search("waitFor") == 0)
			// {
				// stepDescription= "Waiting for <<" + object+ ">>"
			// }
			// else if (action.toLowerCase() ==  ("verifyState".toLowerCase()))
			// {
				// stepDescription= "Verifying the State of the Object <<" + object+ ">> to be " + value
			// }
			// else if ((action.toLowerCase()).search("verify") == 0)
			// {
				// actionText = action.substring("verify".length,action.length);
				// stepDescription= "Verifying " + actionText + " for object <<" + object+ ">> to be " + value
			// }

			// var stepName = "Verifies " + action + " " + object;
			// var stepDesc = action + " " + object + " is done"; 
			// // ******* Reformating the Desrciption End *******

			// var tableRow = new TableRow();
			// tableRow.createLastRow();
			// tableRow.applyColor(result);
			// tableRow.insertRecord(textTag(z+1));
			// tableRow.insertRecord(textTag(id));
			// //tableRow.insertRecord(textTag(sortId));
			// tableRow.insertRecord(textTag(stepDescription));
			// tableRow.insertRecord(textTag(result));
			// tableRow.insertRecord(textTag(errorMessage));
			// //tableRow.insertRecord(textTag(executionTime));
			// tableRow.insertRecord(imageTag(image,"screenshot_icon.gif"));
		// }//steps
	// }//tests
  // }//testsuite
// }

function parseReusableStepLevelXML(xmlFile, doc, appName, browser, baseurl, scenarioId, testCaseId, testCaseSortId) {
	var the_doc = doc;
	var the_testName = scenarioId;
	
	//Environment Details added
	loadEnvironmentDetails(doc, xmlFile);	
	
    appName = white_space(appName);
	browser = white_space(browser);
	var _appName = parseSuiteName(appName);
	if(_appName!=null){
		document.getElementById("teststepname").innerHTML = _appName.toUpperCase();
	}
	document.getElementById("scenario_id").innerHTML = scenarioId;
	
	testSuites = the_doc.getElementsByTagName("TestSuite");
    var h=1
	for(var i = 0; i < testSuites.length; i++) {
		var testSuite = testSuites[i];
		var _suiteName = getAttr(testSuite, "Name");
		suiteName = parseSuiteName(_suiteName);
		var category = getAttr(testSuite, "Category");
		var executionEngine = getAttr(testSuite, "ExecutionEngine");
		var suiteBrowser = getAttr(testSuite, "browser");
		var suiteUrl = getAttr(testSuite, "BaseUrl");

		if( (_suiteName.toLowerCase() != appName.toLowerCase()) ||
			(browser.toLowerCase() != suiteBrowser.toLowerCase()) ||
			(baseurl.toLowerCase() != suiteUrl.toLowerCase()) ){
			continue;
		}

		document.getElementById('browser_name').innerHTML = suiteBrowser;
    	document.getElementById('executionUrl').innerHTML = suiteUrl;
		document.getElementById('category').innerHTML = category;
		document.getElementById('executionEngine').innerHTML = executionEngine;

		var tests = testSuite.getElementsByTagName("Test");

		for(var v = 0; v < tests.length; v++) {
			var test= tests[v];
			var testName = getAttr(test, "id");

			if(testName.toLowerCase() != scenarioId.toLowerCase()){
				continue;
			}			

			var allSteps = test.getElementsByTagName("Steps");
			var steps = allSteps[0].getElementsByTagName("Step");
			var arrLen=1;			
			var sNoArray = new Array();
			var arrCount = 0;


		   for(var z = 0; z < steps.length; z++ ) {
			   			   
			step = null;
			step = steps[z];
			var id = getAttr(step, "id");
			//alert(id);
			
			var sortId = getAttr(step, "sortId");

			 if((id!=testCaseId)){						
				 continue;
			}
			// if(sortId != testCaseSortId){						
				// continue;
			// }			

			var action = getAttr(step, "action");
			var object = getAttr(step, "object");
			var stepDesc = getAttr(step, "description");
			var errorMessage = getAttr(step, "errorMessage");	
			var image = getAttr(step, "image");				
			var result = getAttr(step, "result");
			var stepDescription = getAttr(step, "description");	
			var executionTime = getAttr(step, "executionTime");
			var stepId = getAttr(step, "stepId");
			
			
			// ******* Reformating the Desrciption Starts *******
			object = object.replace(/_/g, " ");
			action = action.replace(/_/g, " ");

			var objType = "";
			var valueIndex = stepDesc.indexOf("parsed value");
			valueIndex = valueIndex + 13;
			var value = stepDesc.substring(valueIndex);
			var actionString = action.toString();
			var objectString = object.toString();				

			
			var tableRow = new TableRow();
			tableRow.createLastRow();
			tableRow.applyColor(result);
			tableRow.insertRecord(textTag(testCaseId)); //New Changes
			//alert(testCaseId);
			//tableRow.insertRecord(textTag(id));
			
			//alert(id);
			tableRow.insertRecord(textTag(stepId));
			
			//tableRow.insertRecord(textTag(sortId));
			tableRow.insertRecord(textTag(stepDescription));
			tableRow.insertRecord(textTag(result));
			tableRow.insertRecord(textTag(executionTime));
			tableRow.insertRecord(textTag(errorMessage));
			tableRow.insertRecord(imageTag(image,"screenshot_icon.gif"));							
			h=h+1;
		}//steps
	}//tests
  }//testsuite
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

function constructHrefTag(linkName, the_name, params) {
	var aTag = document.createElement('a');

	aTag.name = 'a_' + the_name;
	aTag.id = 'a_' + the_name;
	url = linkName;
	if(params != ""){
		url = url + "?" + params;
	}
	
	aTag.href= url;	
	aTag.appendChild(document.createTextNode(the_name));

	return aTag;
}

function constructHrefTagView(linkName, the_name, params) {
	var aTag = document.createElement('a');

	aTag.name = 'a_' + the_name;
	aTag.id = 'a_' + the_name;
	url = linkName;
	if(params != ""){
		url = url + "?" + params;
	}
	
	var image = document.createElement('img');
	image.name = 'img_' + the_name;
	image.id = 'img_' + the_name;
	image.src="../images/images.png";
	aTag.href= url;	
	aTag.appendChild(image);

	return aTag;
}

function constructHrefTagViewspell(linkName, the_name, params,path) {
	var aTag = document.createElement('a');

	aTag.name = 'a_' + the_name;
	aTag.id = 'a_' + the_name;
	url = linkName;
	if(params != ""){
		url = url + "?" + params;
	}
	
	var image = document.createElement('img');
	image.name = 'img_' + the_name;
	image.id = 'img_' + the_name;
	image.src=path;
	aTag.href= url;	
	aTag.appendChild(image);

	return aTag;
}


function constructReplaySSHrefTag(linkName, the_name) {
	var aTag = document.createElement('a');

	aTag.name = 'a_' + the_name;
	aTag.id = 'a_' + the_name;
	url = "javascript:replayScreenshots('" +linkName + "');" ;
	
	aTag.href= url;	
	//aTag.appendChild(document.createTextNode(the_name));
	
	var oImg=document.createElement("img");
    oImg.setAttribute('src', '../images/'+the_name);
    aTag.appendChild(oImg);

	return aTag;
}


function constructParams(params) {
	var theArray = params.split(",");
	var url = "";

	for(var i=0; i<theArray.length; i++) {
		url += theArray[i];
		if((i+1) < theArray.length) {
			url += "&";
		}
	}

	return url;
}

function parseResultList(the_listDoc, the_xmlFileName) {
	//var resultFile = XML_PATH + "TestResultList.xml";
	var resultFile = XML_PATH + "TestResultList.xml";
	
	var parseXmlFile = "";
	var filesList = new Array();
	var listDoc = the_listDoc;

	listDoc.async=false;
	listDoc.load(resultFile);

	var rootNode = listDoc.getElementsByTagName("ResultList");
	var fileNames = listDoc.getElementsByTagName("Result");
	

	for(var v = 0; v < fileNames.length; v++) {
		var fileName = "";
		fileName = fileNames[v];
		xmlFileName = getAttr(fileName, "file");
		//hostName = getAttr(fileName, "hostname");
		//osName = getAttr(fileName, "os");
		parseXmlFile = xmlFileName;
		filesList[v] = xmlFileName;
	}
	//document.getElementById('os_info').innerHTML = osName;
	//document.getElementById('host_info').innerHTML = hostName;
	
	
	
	constructOptions(filesList, the_xmlFileName);
}

function constructOptions(filesList, the_xmlFileName) {

	var xmlFileName = "";
	if(the_xmlFileName.indexOf('./xml/') > 0){
		xmlFileName = the_xmlFileName.split('xml/');
		the_xmlFileName = xmlFileName[1];
	}
    
	var selTag = document.getElementById("xml");
	var optionsLen = filesList.length;
	//selTag.length = optionsLen;
	var k=0;
	var rs_date_actual_temp = "";
	for(var v=optionsLen - 1; v>=0; v--)
	{
		if(rs_date_actual_temp == "")
		{
			xml_file = filesList[v];
			file_str_arr = xml_file.split('_');
			var rs_system_name = file_str_arr[0];
			var rs_date_actual = file_str_arr[1];	
			//rs_date_actual_temp = rs_date_actual
		}
		xml_file = filesList[v];
		file_str_arr = xml_file.split('_');

		if(rs_date_actual_temp != file_str_arr[1])
		{
			var rs_system_name = file_str_arr[0];
			var rs_date_actual = file_str_arr[1];
			rs_date_actual_temp = rs_date_actual
		

			var rs_date_str = file_str_arr[1].substr(0,4)+'-'+file_str_arr[1].substr(4,2)+'-'+file_str_arr[1].substr(6,2);
			
			//var rs_date = new Date(rs_date_str);
			var rs_date = parseISO8601(rs_date_str)
			
			
			//var rs_date = new Date(rs_date_str);
			//alert(rs_date);
			rs_time = file_str_arr[2];
			var month=new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");

			var rs_month = month[rs_date.getMonth()]; 
			var rs_day = rs_date.getDate();
			var rs_yr = rs_date.getFullYear();
			rs_date = rs_month+'-'+rs_day+'-'+rs_yr
			/*var sel = "";
			selTag.options[k].value = filesList[v];
			selTag.options[k].text = filesList[v];
			if(the_xmlFileName==filesList[v])
			{
				selTag.options[k].selected='true';
			}
			k++;*/
			
			var sel = "";
			//alert(the_xmlFileName);
			//alert(filesList[v]);
			selTag.length = k+1;
			selTag.options[k].value = rs_date_actual;
			selTag.options[k].text = rs_date;
			
			if(the_xmlFileName==rs_date_actual)
			{
				selTag.options[k].selected='true';
				glbDate=rs_date;
				document.getElementById("tabletitle").textContent="Test Execution Status For The Day:"+rs_date;
			}
			k++;
		}

	}
	
}
// below function is for working the date function in both ie, and ff without this function date wll return NaN by nmadipally on 09012013 
function parseISO8601(dateStringInRange) 
{
    var isoExp = /^\s*(\d{4})-(\d\d)-(\d\d)\s*$/,
        date = new Date(NaN), month,
        parts = isoExp.exec(dateStringInRange);

    if(parts) {
      month = +parts[2];
      date.setFullYear(parts[1], month - 1, parts[3]);
      if(month != date.getMonth() + 1) {
        date.setTime(NaN);
      }
    }
    return date;

}

function daysBetween(first, second) {

    // Copy date parts of the timestamps, discarding the time parts.
    var one = new Date(first.getFullYear(), first.getMonth(), first.getDate());
    var two = new Date(second.getFullYear(), second.getMonth(), second.getDate());

    // Do the math.
    var millisecondsPerDay = 1000 * 60 * 60 * 24;
    var millisBetween = two.getTime() - one.getTime();
    var days = millisBetween / millisecondsPerDay;

    // Round down.
    return Math.floor(days);
}
function loadOtherResults(what) {
	 var table = document.getElementById("tblSample");  
	 var rowCount = table.rows.length; 

	 for(var i=rowCount; i>1; i--) 
	 {
		 table.deleteRow(i-1);
	 }
	 
	 var the_xmlFile = XML_PATH + document.getElementById("resultsList").value;
		var xmlDoc = null;
		xmlDoc = CreateXMLObject();
		parseSuiteLevelXML(the_xmlFile, xmlDoc);
}

function loadPreviousResults(what) {
	var val = what.value;
	document.resultListForm.submit();
}

function parseTestLevelXML(xmlFile, doc, appName, browser, baseurl, scenarioId){
	var the_doc = doc;
	//Environment Details added
	loadEnvironmentDetails(doc, xmlFile);
	

    appName = white_space(appName);
	browser = white_space(browser);
	var _appName = parseSuiteName(appName);

	//setting Test Case Name and  Scenario Id to Environment Info
	document.getElementById("testcasename").innerHTML = _appName.toUpperCase();
	document.getElementById("scenario_id").innerHTML = scenarioId;

	testSuites = the_doc.getElementsByTagName("TestSuite");

	for(var i = 0; i < testSuites.length; i++) {
		var testSuite = testSuites[i];	 
		var _suiteName = getAttr(testSuite, "Name");
		suiteName = parseSuiteName(_suiteName);
		var category = getAttr(testSuite, "Category");
		var executionEngine = getAttr(testSuite, "ExecutionEngine");
		var suiteBrowser = getAttr(testSuite, "browser");
		var suiteUrl = getAttr(testSuite, "BaseUrl");
		
		if( (_suiteName.toLowerCase() != appName.toLowerCase()) ||
			(browser.toLowerCase() != suiteBrowser.toLowerCase()) ||
			(baseurl.toLowerCase() != suiteUrl.toLowerCase()) ){
			continue;
		}
		document.getElementById('browser_name').innerHTML = suiteBrowser;
    	document.getElementById('executionUrl').innerHTML = suiteUrl;
		document.getElementById('category').innerHTML = category;
		document.getElementById('executionEngine').innerHTML = executionEngine;

		var tests = testSuite.getElementsByTagName("Test");
		for(var v = 0; v < tests.length; v++) {
			var test= tests[v];
			var busId = getAttr(test, "id"); //business id
			//var totalExecutionTime = getAttr(test, "executionTime");	
			
			if(busId.toLowerCase() != scenarioId.toLowerCase()){
				continue;
			}
			
			var allTestCases = test.getElementsByTagName("TestCaseDetails");
			var testCases  = allTestCases[0].getElementsByTagName("TestCase");
			var nextTestCases  = allTestCases[0].getElementsByTagName("TestCase");
			for(var z = 0; z < testCases.length; z++ ) {
				var testCase = null;
				var isPassed = PASS;
				testCase = testCases[z];
				var testCaseId = getAttr(testCase, "id"); //test case id
				var reusable = getAttr(testCase, "reusable"); //test case reusable
				var exetime = getAttr(testCase, "executionTime");
				if(reusable == 'true'){					
					continue;
				}											
				var sortId = getAttr(testCase, "sortId"); //test case sortId
				var testCaseDescription = getAttr(testCase, "description");
				
				var totalExecutionTime = getTestCaseExecutionDuration(test, testCaseId);
				if(totalExecutionTime == 'NA')
					totalExecutionTime = getAttr(test, "executionTime");

				var testResult = getTestCaseResultskip(testCaseId, test);

				if(testResult == 'SKIP') {
					isPassed = SKIP;
				}
				if(testResult == 'FAIL') {
					isPassed = FAIL;
				} 



				var params = "apps=" + appName + "&browser=" + browser + "&baseurl=" + escape(baseurl) + "&xml="
						+ xmlFile + "&test=" + scenarioId +"&resultType=" + testResult + "&testCaseId=" 
						+ testCaseId +"&sortId=" + sortId;

				var testCaseIdTag = constructHrefTag(TeststepHTML, testCaseId, params);
				testCaseIdTag.className= testResult;

				var data = textTag(isPassed);
				
				var tableRow = new TableRow();
				tableRow.createLastRow();
				tableRow.applyColor(isPassed);
if (isPassed!="SKIP")
{				
				tableRow.insertRecord(testCaseIdTag);
}else
{
tableRow.insertRecord(textDescTag(testCaseId));
}				
				tableRow.insertRecord(textDescTag(testCaseDescription));
				tableRow.insertRecord(data);
				tableRow.insertRecord(textTag(exetime));

			  }
	   
		 }
	}
			
}
function loadEnvironmentDetails(doc, xmlFile){
	var the_doc = doc;
	the_doc.async=false;
	the_doc.load(xmlFile);
    //alert(xmlFile);
	var testResults = the_doc.getElementsByTagName("Environment");
	var environmentDetails = testResults[0];
	
	var oS = getAttr(environmentDetails, "os");
	var hostname = getAttr(environmentDetails, "hostname");
	var overallStartTime = getAttr(environmentDetails, "startTime");
	var overallEndTime = getAttr(environmentDetails, "endTime");
	
	var logFile = getAttr(environmentDetails, "logFile");
  	    logFile = logFile.replace("//", "/");

	document.getElementById('os_info').innerHTML = oS;
	document.getElementById('host_info').innerHTML = hostname;
	document.getElementById("start_time").innerHTML = overallStartTime;
	document.getElementById("end_time").innerHTML = overallEndTime;	
}

function getParsedValue(xmlFile, doc, appName, browser, baseurl, scenarioId, testCaseId) {
	var the_doc = doc;
	the_doc.async=false;
	the_doc.load(xmlFile);

	var the_testName = scenarioId;
	var reusableTestCaseArr="";
    appName = white_space(appName);
	browser = white_space(browser);
	testSuites = the_doc.getElementsByTagName("TestSuite");

	for(var i = 0; i < testSuites.length; i++) {
		var testSuite = testSuites[i];
		var suiteName = getAttr(testSuite, "Name");
		var category = getAttr(testSuite, "Category");
		var suiteBrowser = getAttr(testSuite, "browser");
		var suiteUrl = getAttr(testSuite, "BaseUrl");

		if( (suiteName.toLowerCase() != appName.toLowerCase()) ||
			(browser.toLowerCase() != suiteBrowser.toLowerCase()) ||
			(baseurl.toLowerCase() != suiteUrl.toLowerCase()) ){
			continue;
		}

		var tests = testSuite.getElementsByTagName("Test");
		for(var v = 0; v < tests.length; v++) {
			var test= tests[v];
			var testName = getAttr(test, "id");

			if(testName.toLowerCase() != scenarioId.toLowerCase()){
				continue;
			}
			
			var allSteps = test.getElementsByTagName("Steps");
			var steps = allSteps[0].getElementsByTagName("Step");
			var reusableTestCase;

			for(var z = 0; z < steps.length; z++ ) {
				step = null;
				step = steps[z];
				var id = getAttr(step, "id");

 				if(testCaseId.length > 0 && id.toLowerCase() != testCaseId.toLowerCase()){
				 continue;
			    }

				var stepDesc = getAttr(step, "description");
				var valueIndex = stepDesc.indexOf("parsed value");

				if(valueIndex != -1){
					valueIndex = valueIndex + 14;
					reusableTestCase = stepDesc.substring(valueIndex, stepDesc.length-1);

					if(reusableTestCase.indexOf('RS') == 0 || reusableTestCase.indexOf('novalue') == 0 
						|| reusableTestCase.indexOf('true') == 0){
						reusableTestCaseArr = reusableTestCaseArr + ':' + reusableTestCase;	
					}
					else{
						reusableTestCaseArr = ':' +reusableTestCase;	
					}
				}else{ // This step doesn't have any reusables
					reusableTestCaseArr = ':' +testCaseId;
				}
			}
		}
	}

	return reusableTestCaseArr;
}
/**
* This function replace the occurence of '%20' with white space in the string
*/
function white_space(str)
{
	if(str!=null){
		str = str.replace(/%20/gi, " ");
	}
 return str;
}

/**
*This function returns the Result of testcase
*/
function getTestCaseResult(stepSortId, testObj){
var allSteps = testObj.getElementsByTagName("Steps");
var steps = allSteps[0].getElementsByTagName("Step");
var result = "PASS";

for(var z = 0; z < steps.length; z++ ) {
	step = null;
	step = steps[z];
	var sortId = getAttr(step, "sortId");

	if(stepSortId != sortId){
	 continue;
	}
	result = getAttr(step, "result");	

	if(result == 'SKIP'){
		break;
	} 
	if(result == 'FAIL'){
		break;
	} 
 }	

 return result;
}

/**
*This function returns the Result of testcase
*/
function getTestCaseResultskip(stepSortId, testObj){
var allSteps = testObj.getElementsByTagName("Steps");
var steps = allSteps[0].getElementsByTagName("Step");
var result = "PASS";

for(var z = 0; z < steps.length; z++ ) {
	step = null;
	step = steps[z];
	var sortId = getAttr(step, "id");

	if(stepSortId != sortId){
	 continue;
	}
	result = getAttr(step, "result");	

	if(result == 'SKIP'){
		break;
	} 
	if(result == 'FAIL'){
		break;
	} 
 }	

 return result;
}




/**
* Printing the Reusable Test Cases
*/
function displayReusableData(testObj, arrObj) {		
			
	var allSteps = testObj.getElementsByTagName("Steps");
	var steps = allSteps[0].getElementsByTagName("Step");
	var arrLen=1;			
	var sNoArray = new Array();
	var arrCount = 0;

	for(var i = 0; i <= arrObj.length; i++ ) {
		for(var z = 0; z < steps.length; z++ ) {
		step = null;
		step = steps[z];
		
        var selRow = arrObj[i];

		if(z == selRow){				
			var action = getAttr(step, "action");
			var object = getAttr(step, "object");
			var id = getAttr(step, "id");
			var stepDesc = getAttr(step, "description");
			var errorMessage = getAttr(step, "errorMessage");	
			var image = getAttr(step, "image");				
			var result = getAttr(step, "result");
			var stepDescription = getAttr(step, "description");	
			var sortId = getAttr(step, "sortId");	

			// ******* Reformating the Desrciption Starts *******
			object = object.replace(/_/g, " ");
			action = action.replace(/_/g, " ");

			var objType = "";
			var valueIndex = stepDesc.indexOf("parsed value");
			valueIndex = valueIndex + 13;
			var value = stepDesc.substring(valueIndex);
			var actionString = action.toString();
			var objectString = object.toString();				

			if((action.toLowerCase()) == ("click".toLowerCase()))
			{
				if(objectString.search("btn ") == 0)
					objType= "button ";
				else if(objectString.search("lnk") == 0)
					objType= "link ";
				else if(objectString.search("spn") == 0)
					objType= "span ";
				stepDescription= "Clicking on the " + objType + "<<" + object+ ">>";
			}
			else if ((action.toLowerCase()) == ("type".toLowerCase()))
			{
				stepDescription= "Typing the Text " + value + " in <<" + object+ " >>"
			}
			else if ((action.toLowerCase()) == ("wait".toLowerCase()))
			{
				stepDescription= "Waiting till " + value + " nano seconds "
			}
			else if ((action.toLowerCase()) == ("waitForPagetoLoad".toLowerCase()))
			{
				stepDescription= "Waiting for page to load";
			}
			else if (action.search("waitFor") == 0)
			{
				stepDescription= "Waiting for <<" + object+ ">>"
			}
			else if (action.toLowerCase() ==  ("verifyState".toLowerCase()))
			{
				stepDescription= "Verifying the State of the Object <<" + object+ ">> to be " + value
			}
			else if ((action.toLowerCase()).search("verify") == 0)
			{
				actionText = action.substring("verify".length,action.length);
				stepDescription= "Verifying " + actionText + " for object <<" + object+ ">> to be " + value
			}

			var stepName = "Verifies " + action + " " + object;
			var stepDesc = action + " " + object + " is done"; 
			// ******* Reformating the Desrciption End *******

			var tableRow = new TableRow();
			tableRow.createLastRow();
			tableRow.applyColor(result);
			tableRow.insertRecord(textTag(z+1));
			tableRow.insertRecord(textTag(id));
			tableRow.insertRecord(textTag(sortId));
			tableRow.insertRecord(textTag(stepDescription));
			tableRow.insertRecord(textTag(result));
			tableRow.insertRecord(textTag(errorMessage));
			tableRow.insertRecord(imageTag(image,"screenshot_icon.gif"));
			
		 }	
	   }
	 }		  	 

	}

function getTestCaseArray(testSuiteObj){
	var testCaseArr = new Array();
	var cnt=0;
	var tests = testSuiteObj.getElementsByTagName("Test");
	
   
	for(var v = 0; v < tests.length; v++) {
		var test= tests[v];
		var allTestCases = test.getElementsByTagName("TestCaseDetails");
	    var testCases  = allTestCases[0].getElementsByTagName("TestCase");
		for(var j=0; j <testCases.length; j++){
			var testCaseName = getAttr(test, "id");
//			alert('testCaseName ='+testCaseName );
			testCaseArr[cnt] = testCaseName;
			cnt++;
		}
	}
	
	return testCaseArr;
}
/**
* This function calculates the Total execution duratin for a specific Test case.
*/
function getTestCaseExecutionDuration(testObj, testCaseSortId){
	var allSteps = testObj.getElementsByTagName("Steps");
	var steps = allSteps[0].getElementsByTagName("Step");
	var reusableTestCase;
	var mmArr = new Array();
	var ssArr = new Array();
	var mssArr = new Array();  //millisec
	var cnt = 0;
	var executionTime = "";
	for(var z = 0; z < steps.length; z++ ) {
		step = null;
		step = steps[z];
		var id = getAttr(step, "id");
		var sortId = getAttr(step, "sortId");
		var reusable = getAttr(step, "reusable");
		
		//if(sortId == testCaseSortId && reusable != 'true'){
		if(sortId == testCaseSortId ){

			executionTime = getAttr(step, "executionTime"); // mm:ss:sss			
			var executionTimeArr = executionTime.split(':');						
			mmArr[cnt] = executionTimeArr[0]; //mm
			ssArr[cnt] = executionTimeArr[1]; //ss
			mssArr[cnt] = executionTimeArr[2]; //sss
			cnt++;
			
		}	
	}
	var testCaseExecDuration = "";
	if(executionTime == ""){
		testCaseExecDuration = "NA";
	}else{
		var ss = 0; //seconds
		var mSec = 0; //milliseconds
		var min = 0;
		var extSec = 0;
		var extMin = 0;
		var extSec = 0;
		var extmSec = 0;
		var Temp1=0;

		for(var i=0; i<mssArr.length; i++){
			// mSec = parseInt(mSec) + parseInt(mssArr[i]);
			 str1 = mssArr[i].replace ( '0', '' );

			 mSec = parseFloat(mSec) + parseFloat(str1);
		}
		//if milliseconds > 1000
		if(parseInt(mSec) > 1000){
			extSec = parseInt(mSec / 1000);
			mSec = parseInt(mSec % 1000);			
		}


		for(var j=0; j<ssArr.length; j++){
			 ss = parseFloat(ss) + parseFloat(ssArr[j]);
		}
		ss = parseFloat(ss) + parseFloat(extSec);
		//if seconds > 60
		if(parseInt(ss) > 60){
			 extMin = extMin + parseInt(ss / 60);
			 ss = parseInt(ss % 60);			 
		}

		for(var k=0; k<mmArr.length; k++){
			 min = parseFloat(min) + parseFloat(mmArr[k]);
		}
		min = parseFloat(min) + parseFloat(extMin);

		//formatting milliseconds
		if(mSec.toString().length == 1)
			mSec = "00" + mSec;
		else if (mSec.toString().length == 2)
			mSec = "0" + mSec;
	    //formatting seconds
		if(ss.toString().length == 1)
			 ss = "0" + ss;
		//formatting minutes
		if(min.toString().length == 1)
			 min = "0" + min;

		testCaseExecDuration = min +':'+ ss + ':' + mSec;

	}
	return testCaseExecDuration;
	
}

function parseSuiteName(suiteName){
	var appName = suiteName;
 if(suiteName!=null){
	if(suiteName.indexOf('^') > 0){
		var suiteNameArr = suiteName.split('^');
		appName = suiteNameArr[0];
	}
 }	
	return appName;
}


function setreturndata(noOfReports){
this.trendReport(noOfReports);

	return setarray1;
	
}

function setPassData(noOfReports){

this.trendReport(noOfReports);

	return FinalSetPass1;
	
}

function setFailData(noOfReports){

this.trendReport(noOfReports);

	return FinalSetFail1;
	
	
}


function Browserreturndata(noOfReports){
this.trendReport(noOfReports);

	return BrowserArray1;
	
}

function BrowserPassData(noOfReports){

this.trendReport(noOfReports);

	return FinalBrowserPass1;
	
}

function BrowserFailData(noOfReports){

this.trendReport(noOfReports);

	return FinalBrowserFail1;
	
	
}

function diffOf2Dates(todaysDate,configDate)
{
	var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
	var firstDate = todaysDate; // Todays date
	var secondDate = new Date(configDate);

	var diffDays = Math.abs((firstDate.getTime() - secondDate.getTime())/(oneDay));
	//console.info(firstDate+", "+secondDate);

	return Math.ceil(diffDays);
}

function Lastdateofgivendate(currentDate)
{
	var mydate= new Date(currentDate)
	mydate.setDate(mydate.getDate()-1)
	oneDaybefore= (mydate.getMonth()+1)+"/"+(mydate.getDate())+"/"+mydate.getFullYear();
	return oneDaybefore;

}

function Lastdateofgivendate(currentDate,days)
{
	var mydate= new Date(currentDate)
	mydate.setDate(mydate.getDate()-days)
	oneDaybefore= (mydate.getMonth()+1)+"/"+(mydate.getDate())+"/"+mydate.getFullYear();
	return oneDaybefore;

}

var Test_TestCasePass=0;
var Test_TestCaseFail=0;
var Test_TestSenarioPass=0;
var Test_TestSenarioFail=0;

function load(the_xmlFileName)
{
 			var parseQueryString = new ParseQueryString();
			//var the_xmlFileName = parseQueryString.getParamValue('xml');
			
			if(!the_xmlFileName) {
				the_xmlFileName = "";
			}

			var listDoc = null;
			listDoc = CreateXMLObject();
			var xmlFileName=the_xmlFileName.split("/")[2];

			//var the_xmlFile = XML_PATH+document.getElementById("xml").value;								
			var the_doc = listDoc;

	the_doc.async=false;
	the_doc.load(the_xmlFileName);
	var testResults = the_doc.getElementsByTagName("Environment");	
	var environmentDetails = testResults[0];
	var oS = getAttr(environmentDetails, "os");
	var hostname = getAttr(environmentDetails, "hostname");
	var overallStartTime = getAttr(environmentDetails, "startTime");
	var overallEndTime = getAttr(environmentDetails, "endTime");
	var overallExecutionTime = getAttr(environmentDetails, "totalExecutionDuration");		
	var logFile = getAttr(environmentDetails, "logFile");
  	logFile = logFile.replace("//", "/");		
	document.getElementById('os_info').innerHTML = oS;
	document.getElementById('host_info').innerHTML = hostname;
	document.getElementById("start_time").innerHTML = overallStartTime;
	document.getElementById("end_time").innerHTML = overallEndTime;
	//document.getElementById("total_execution_duration").innerHTML = overallExecutionTime;
	testSuites = the_doc.getElementsByTagName("TestSuite");	
	var cumPassCount = 0;
	var cumFailCount = 0;
	var cumTotalCount = 0;
	var stepTotalPassCount = 0;
	var stepTotalFailCount = 0;
	for(var i = 0; i < testSuites.length; i++) {
		var passCount = 0;
		var failCount = 0;
		var totalCount = 0;
		var testSuite = testSuites[i];
		var _suiteName = getAttr(testSuite, "Name");	
		suiteName = parseSuiteName(_suiteName);
		var category = getAttr(testSuite, "Category");	
		var executionEngine = getAttr(testSuite, "ExecutionEngine");	
		var totalExecutionTime = getAttr(testSuite, "executionTime");
		var browser = getAttr(testSuite, "browser");
		var BaseUrl = getAttr(testSuite, "BaseUrl");
		var tests = testSuite.getElementsByTagName("Test");

		for(var v = 0; v < tests.length; v++) {
			var test= tests[v];
			var isPassed = PASS;
			var isFailed = FAIL;
			var resultTag = test.getElementsByTagName("Result");
			var allSteps = test.getElementsByTagName("Steps");
			var steps = allSteps[0].getElementsByTagName("Step");			
			isPassed = getNodeValue(resultTag[0]);
			isFailed = getNodeValue(resultTag[0]);
			if(isPassed.toLowerCase() == PASS.toLowerCase()) {
				passCount = parseInt(passCount) + 1;
			}
			else if (isFailed.toLowerCase() == FAIL.toLowerCase()){
				failCount = parseInt(failCount) + 1;
			}
			else{
				passCount = parseInt(passCount) + 1;
			}

			totalCount = totalCount + 1;
			for(var z = 0; z < steps.length; z++ ) {
			
			var stepPassCount = 0;
			var stepFailCount = 0;
			step = null;
			step = steps[z];
			var testCasResult = getAttr(step, "result");
			if(testCasResult.toLowerCase() == PASS.toLowerCase()) {
				stepPassCount = parseInt(stepPassCount) + 1;
			}
			else {
				stepFailCount = parseInt(stepFailCount) + 1;
			}
			stepTotalPassCount =parseInt(stepTotalPassCount) + parseInt(stepPassCount);
			stepTotalFailCount =parseInt(stepTotalFailCount) + parseInt(stepFailCount); // RK changed this step for wrong fail test case count. 

			}
		}
		
		cumPassCount = parseInt(cumPassCount) + parseInt(passCount);
	    cumFailCount = parseInt(cumFailCount) + parseInt(failCount);
	   // cumTotalCount = parseInt(cumTotalCount) + parseInt(totalCount);
 	}
	
			Test_TestCasePass=stepTotalPassCount;
			Test_TestCaseFail=stepTotalFailCount;
			Test_TestSenarioPass=cumPassCount;
			Test_TestSenarioFail=cumFailCount;

			drawChart_graph();
			//drawChart_graph1();
}
function contains(a, obj) {
    var i = a.length;
    while (i--) {
       if (a[i] === obj) {
           return true;
       }
    }
    return false;
}

function containsElement(a, obj) {
    var i = a.length;
	var requiredvalue=0;
    while (i--) {
		var s=a[i];
       if (s[0] === obj) {
		   requiredvalue=i;
       }
    }
    return requiredvalue;
}

function drawChart(Datatext) 
{        
  document.getElementById('pie_title').innerHTML=Datatext;	
  jQuery.jqplot.config.enablePlugins = true;
  plot7 = jQuery.jqplot('pie_chart',
    [[["Pass["+DatecumPassCount+"]", DatecumPassCount],["Fail["+DatecumFailCount+"]", DatecumFailCount]]],
    {
      title: '',
      seriesDefaults: {shadow: true, renderer: jQuery.jqplot.PieRenderer, rendererOptions: { showDataLabels: true ,

 borderWidth: 0} },
	  drawBorder: true,
	  seriesColors: [ "#0F6700", "#9C0000"],
      legend: { show:true },
	  		     cursor:
		        {
			  show: false,
			  zoom: true,
			  showTooltip: true
			  
		        },
    }
  );
	  Datatext="";        
	DatecumFailCount=0;
 }
 
		
		
  

   
	function drawTestSetBarChart() 
	{
		// var data = google.visualization.arrayToDataTable(suiteCountPassArr);
		var x=suiteCountPassArr.length
		testsetNamearr=new Array();
		testsetpassarr=new Array();
		testsetfailarr=new Array();
		testsetNamearr1=new Array();

		for (i=0;i<suiteCountPassArr.length;i++)
		{
			testsetNamearr.push(suiteCountPassArr[i][0]);
			testsetpassarr.push(suiteCountPassArr[i][1]);
			testsetfailarr.push(suiteCountPassArr[i][2]);
			if(suiteCountPassArr[i][0].length >=4)
			{
				var dd= suiteCountPassArr[i][0];
			//	dd=dd+"..";
				testsetNamearr1.push(dd);
			}
		}
		if (testsetpassarr.length==1)
		 {
		 testsetpassarr.push(0);
		 testsetfailarr.push(0);
		 
		 }
		
		// Can specify a custom tick Array.
		// Ticks should match up one for each y value (category) in the series.
		var ticks = testsetNamearr1;
		var ticks1=testsetNamearr;
		var plot1 = $.jqplot('TestSet_barChart', [testsetpassarr, testsetfailarr], {
		stackSeries:true,
		animate: !$.jqplot.use_excanvas,
		title: "",
		seriesDefaults:{
		renderer:$.jqplot.BarRenderer,
		rendererOptions: {fillToZero: true}
		},
		seriesColors: [ "#0F6700", "#9C0000"],
		series:[
		{label:'Pass'},
		{label:'Fail'}
		],

		highlighter:
		{
		show: true,
		//sizeAdjust: 7.5,
		tooltipContentEditor:tooltipContentEditor  //new code added to attempt to add legend name to mouse over tool tip
		},
		cursor:
		{
		show: false,
		zoom: true,
		showTooltip: true

		},			
		legend:
		{
		//labels: ticks,
		labels1:ticks1,
		show: true,
		//location: 'e',
		//renderer: $.jqplot.EnhancedLegendRenderer,
		rendererOptions:
		{
		// numberColumns: 10, 
		// numberRows: 5,
		// seriesToggle: 900,
		// disableIEFading: true
		},
		/*marginTop: '100px',
		marginBottom: '100px',*/
		placement: 'outside'
		}, 
		axes: {
		// Use a category axis on the x axis and use our custom ticks.
		xaxis: {
		renderer: $.jqplot.CategoryAxisRenderer,
		ticks: ticks,

		},
		// Pad the y axis just a little so bars can get close to, but
		// not touch, the grid boundaries.  1.2 is the default padding.
		yaxis: {
		//pad: 0.05,
		tickOptions: {formatString: ''}
		}
		}
		});
		suiteCountPassArr=new Array();
	}   
   
	function tooltipContentEditor(str, seriesIndex, pointIndex, plot)
	{
		   // display series_label, x-axis_tick, y-axis value
			//return plot.legend.labels[seriesIndex] + ", " + plot.data[seriesIndex][pointIndex];
			return plot.legend.labels1[pointIndex]+ ", " + plot.data[seriesIndex][pointIndex];
		   //return plot.legend.labels[seriesIndex] + ", " + str;
	}
   
	function drawTestSetBarChart1() 
	{
		testsetNamearr=new Array();
		testsetpassarr=new Array();
		testsetfailarr=new Array();
		testsetNamearr1=new Array();
		for (i=0;i<suiteCountBrowserArr.length;i++)
		{
			testsetNamearr.push(suiteCountBrowserArr[i][0]);
			testsetpassarr.push(suiteCountBrowserArr[i][1]);
			testsetfailarr.push(suiteCountBrowserArr[i][2]);
			// below code is commented by Suresh Kumar B.. 
		//	if((suiteCountBrowserArr[i][0].length >=4)||(suiteCountBrowserArr[i][0].length ==""))
		//	{
				var dd= suiteCountBrowserArr[i][0];
				// dd=dd+"..";		
				testsetNamearr1.push(dd);
		//	}
		}		
		if(testsetNamearr=="")
		{
			var control1 = document.getElementById('TestSet_barChart111');
			control1.style.visibility = "hidden";
		}
        
		 if (testsetpassarr.length==1)
		 {
		 testsetpassarr.push(0);
		 testsetfailarr.push(0);
		 
		 }
		// Can specify a custom tick Array.
		// Ticks should match up one for each y value (category) in the series.
		var ticks = testsetNamearr1;
		var ticks1=testsetNamearr;
		
		var plot1 = $.jqplot('TestSet_barChart1',[testsetpassarr,testsetfailarr],{
		// The "seriesDefaults" option is an options object that will
		// be applied to all series in the chart.
		stackSeries:true,
		animate: !$.jqplot.use_excanvas,
		title: "",
            seriesDefaults:
            {
                renderer:$.jqplot.BarRenderer,
                rendererOptions:
                {
                  barMargin: 30,
                  highlightMouseDown: true  
               },
               pointLabels: {show: true}
            },

		
		series:[{label:'Pass'},{label:'Fail'}],	
		seriesColors: [ "#0F6700", "#9C0000"],
		highlighter:
		{
			show: true,
			//sizeAdjust: 7.5,
			tooltipContentEditor:tooltipContentEditor  //new code added to attempt to add legend name to mouse over tool tip
		},
		cursor:
		{
			show: false,
			zoom: true,
			showTooltip: false

		},			
		legend:
		{
			labels1:ticks1,
			show: true,
			//location: 'e',
			//renderer: $.jqplot.EnhancedLegendRenderer,
			rendererOptions:
			{
			// numberColumns: 10, 
			// numberRows: 5,
			// seriesToggle: 900,
			// disableIEFading: true
			},
			/*marginTop: '100px',
			marginBottom: '100px',*/
			placement: 'outside'
		}, 
		axes: {
		// Use a category axis on the x axis and use our custom ticks.
		xaxis: {
			renderer: $.jqplot.CategoryAxisRenderer,
			ticks: ticks,

		},

		yaxis: {
			//pad: 0.05,
			tickOptions: {formatString: ''}
		}
		},

		});
		suiteCountBrowserArr=new Array();
		testsetNamearr=new Array();
		testsetpassarr=new Array();
		testsetfailarr=new Array();
	}
//{color:'Test Execution Browser Status', fontName: <string>, fontSize: <number>}
   var TodayDate_Pass=0;
   var TodayDate_Fail=0;
   var YDate_Pass=0;
   var YDate_Fail=0;
   var SevenDate_Pass=0;
   var SevenDate_Fail=0;
   var FinalDay_pass=0;
   var FinalDay_Fail=0;
   
   
function TrendDataForBarcharts()
{

		var reportFlag=true;
		var browserName  = navigator.appName;	
		var nAgt = navigator.userAgent;
		if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
			reportFlag=false;
		}
 			var parseQueryString = new ParseQueryString();
			var the_xmlFileName = parseQueryString.getParamValue('TestResultList.xml');
			
			if(!the_xmlFileName) {
				the_xmlFileName = "";
			}

			var listDoc = null;
			listDoc = CreateXMLObject();
			//parseResultList(listDoc, the_xmlFileName);
			var the_xmlFile = XML_PATH +"TestResultList.xml";								
			var the_doc = listDoc;
			the_doc.async=false;
			the_doc.load(the_xmlFile);
			
	//		var listDoc = null;
		var testSenario_pass=0;
		var testSenario_Fail=0
		 YDate_Pass=0;
		 YDate_Fail=0;
	//	listDoc = CreateXMLObject();
	//	var resultFile = "./templates/TestDataResultList.xml";	
		
	//	listDoc.load(resultFile);


		
		var rootNode = the_doc.getElementsByTagName("ResultList");	
		var fileNames = rootNode[0].getElementsByTagName("Result");	
		for(var v = fileNames.length-1;v>=0; v--) {
		  var TestSuiteCount=fileNames[v].getElementsByTagName("TestSuite");
		 // var filesList=fileNames[v].getElementsByTagName("Result");     
			var xmlFileName1 = getAttr(fileNames[v], "file");
			var xmlFileName=xmlFileName1.split("_")[1];
			var Actuvalfile = xmlFileName.substring(4,6)+"/"+xmlFileName.substring(6,8)+"/"+xmlFileName.substring(0,4)
			var toDayDate = new Date();		
			var finalDates =diffOf2Dates(toDayDate,Actuvalfile);			
			for(var k = 0; k < TestSuiteCount.length; k++) {
			
				if(reportFlag==true){
					 testSenario_pass=parseInt(testSenario_pass)+parseInt(TestSuiteCount[k].children[0].textContent);		
					 testSenario_Fail=parseInt(testSenario_Fail)+parseInt(TestSuiteCount[k].children[1].textContent);
					}else {
						testSenario_pass=parseInt(testSenario_pass)+parseInt(TestSuiteCount[k].getElementsByTagName("Test_Pass")[0].text);		
						 testSenario_Fail=parseInt(testSenario_Fail)+parseInt(TestSuiteCount[k].getElementsByTagName("TestCase_Fail")[0].text);
					}
				  if (finalDates==1)
				  {
				   TodayDate_Pass=testSenario_pass;
				   TodayDate_Fail=testSenario_Fail;
				  }
				  else if (finalDates==2)
				  { if(reportFlag==true){
						YDate_Pass=parseInt(YDate_Pass)+parseInt(TestSuiteCount[k].children[0].textContent);		
						YDate_Fail=parseInt(YDate_Fail)+parseInt(TestSuiteCount[k].children[1].textContent);
					  }else {
						  YDate_Pass=parseInt(YDate_Pass)+parseInt(TestSuiteCount[k].getElementsByTagName("Test_Pass")[0].text);		
						  YDate_Fail=parseInt(YDate_Fail)+parseInt(TestSuiteCount[k].getElementsByTagName("TestCase_Fail")[0].text);
					  }}
				 else if (finalDates<=7)
				  {
				  SevenDate_Pass=testSenario_pass;
				  SevenDate_Fail=testSenario_Fail;
				  }
				  
				  
			}
			
		}
		  FinalDay_pass=testSenario_pass;
		  FinalDay_Fail=testSenario_Fail;
		 
} 



var suiteCountPassArr=new Array();
var suiteCountBrowserArr=new Array();
function TrendDataBarcharts_Sets()
{

	var reportFlag=true;
	var browserName  = navigator.appName;
	var nAgt = navigator.userAgent;	
	if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
		reportFlag=false;
	}
 			var parseQueryString = new ParseQueryString();
			var the_xmlFileName = parseQueryString.getParamValue('TestResultList.xml');
			
			if(!the_xmlFileName) {
				the_xmlFileName = "";
			}

			var listDoc = null;
			listDoc = CreateXMLObject();

			//parseResultList(listDoc, the_xmlFileName);
			var the_xmlFile = XML_PATH+"TestResultList.xml";								
			var the_doc = listDoc;
			the_doc.async=false;
			the_doc.load(the_xmlFile);
			
	//		var listDoc = null;
		var testSenario_pass=0;
		var testSenario_Fail=0
		//suiteCountPassArr="";
		var suiteNameArr= new Array();
		var browserArr= new Array();			
		var s=new Array();
		s=["Rama","Pass","Fail"];
       // suiteCountPassArr.push(s);
		//suiteCountBrowserArr.push(s);

		var rootNode = the_doc.getElementsByTagName("ResultList");	
		var fileNames = rootNode[0].getElementsByTagName("Result");	
		for(var v = fileNames.length-1;v>=0; v--) {
		  var TestSuiteCount=fileNames[v].getElementsByTagName("TestSuite");
		 // var filesList=fileNames[v].getElementsByTagName("Result");     
			var xmlFileName1 = getAttr(fileNames[v], "file");
			var xmlFileName=xmlFileName1.split("_")[1];
			var Actuvalfile = xmlFileName.substring(4,6)+"/"+xmlFileName.substring(6,8)+"/"+xmlFileName.substring(0,4)
			var toDayDate = new Date();		
			var finalDates =diffOf2Dates(toDayDate,Actuvalfile);			
			for(var k = 0; k < TestSuiteCount.length; k++) {
			  var setName = getAttr(TestSuiteCount[k], "Name");
			   var BrowserName = getAttr(TestSuiteCount[k], "browser");			   
				 
				 // testSenario_pass=parseInt(testSenario_pass)+parseInt(TestSuiteCount[k].children[0].textContent);		
				 // testSenario_Fail=parseInt(testSenario_Fail)+parseInt(TestSuiteCount[k].children[1].textContent);
				 
			   if(reportFlag==true){					 
					 testSenario_pass=parseInt(TestSuiteCount[k].children[0].textContent);		
					 testSenario_Fail=parseInt(TestSuiteCount[k].children[1].textContent);
					 }else{
						 testSenario_pass=parseInt(TestSuiteCount[k].getElementsByTagName("Test_Pass")[0].text);		
						 testSenario_Fail=parseInt(TestSuiteCount[k].getElementsByTagName("TestCase_Fail")[0].text);
					 }
				 				if(!this.contains(suiteNameArr,setName)){
										//var s=new Array();
										s=[setName,testSenario_pass,testSenario_Fail];
										suiteCountPassArr.push(s);
										suiteNameArr.push(setName);									
										//suiteCountPassArr.push(setName+","+testSenario_pass+","+testSenario_Fail);
										//suiteCountFailArr.push(testSenario_Fail);
									}else{
									var e=this.containsElement(suiteCountPassArr,setName);
									var requiredArray=suiteCountPassArr[e];
									requiredArray[0]=requiredArray[0];
									requiredArray[1]=requiredArray[1]+testSenario_pass;
									requiredArray[2]=requiredArray[2]+testSenario_Fail;
									suiteCountPassArr[e]=requiredArray;
									
									}
									
									if(!this.contains(browserArr,BrowserName)){
										//var s=new Array();
										s=[BrowserName,testSenario_pass,testSenario_Fail];
										suiteCountBrowserArr.push(s);
										browserArr.push(BrowserName);									
										//suiteCountPassArr.push(setName+","+testSenario_pass+","+testSenario_Fail);
										//suiteCountFailArr.push(testSenario_Fail);
									}else{
									var e=this.containsElement(suiteCountBrowserArr,BrowserName);
									var requiredArray=suiteCountBrowserArr[e];
									requiredArray[0]=requiredArray[0];
									requiredArray[1]=requiredArray[1]+testSenario_pass;
									requiredArray[2]=requiredArray[2]+testSenario_Fail;
									suiteCountBrowserArr[e]=requiredArray;
									}	
									
			}
			
		}
		

} 






function drawChart_graph()
{
		
		  jQuery.jqplot.config.enablePlugins = true;
			plot7 = jQuery.jqplot('pie_chart1',
    [[["Pass ["+Test_TestSenarioPass+"]", Test_TestSenarioPass],["Fail ["+Test_TestSenarioFail+"]", Test_TestSenarioFail]]],
    {
      title: "Test Scenario Execution Status",
	    grid: { 
                 drawBorder: true 
              }, 
      seriesDefaults: {borderWidth:1,shadow: false, renderer: jQuery.jqplot.PieRenderer, rendererOptions: { 



 

 showDataLabels: true,
 borderWidth: 1 } },
	  seriesColors: [ "#0F6700", "#9C0000"],
      legend: { show:true }
    }
  );
		Test_TestSenarioPass=0;
		Test_TestSenarioFail=0;

}


function drawChart_graph1()
{

		
		
		  jQuery.jqplot.config.enablePlugins = true;
			plot7 = jQuery.jqplot('pie_chart2',
    [[["Pass ["+Test_TestCasePass+"]", Test_TestCasePass],["Fail ["+Test_TestCaseFail+"]", Test_TestCaseFail]]],
    {
      title: "Test Case Execution Status",
	  	    grid: { 
                              drawBorder: true 
                          }, 
      seriesDefaults: {borderWidth:1,shadow: false, renderer: jQuery.jqplot.PieRenderer, rendererOptions: { showDataLabels: true,

 borderWidth: 0 } },

	  seriesColors: [ "#0F6700", "#9C0000"],
      legend: { show:true}
    }
  );
  
		Test_TestCasePass=0;
		Test_TestCaseFail=0;

}

function function1a(){
	alert("ssssss");
}
 function drawTestProcessBarChart() 
  {
        

		
	var s1 = [FinalDay_pass, SevenDate_Pass, YDate_Pass, TodayDate_Pass];
    var s2 = [FinalDay_Fail,SevenDate_Fail,YDate_Fail, TodayDate_Fail];
   
    // Can specify a custom tick Array.
    // Ticks should match up one for each y value (category) in the series.
    var ticks = ['Total', 'Last 7 Day`s', 'Yesterday', 'Today'];
	
	
     
    var plot1 = $.jqplot('TestProcess_barChart', [s2, s1], {
	
	//title: "Test Progress report",
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
		 stackSeries:false,
		 animate: !$.jqplot.use_excanvas,
        seriesDefaults:{			
			    renderer:$.jqplot.BarRenderer,
			    rendererOptions: 
			    {
				 
				  highlightMouseDown: true ,
				  fillToZero: true
			    },
			    //pointLabels: { show: true,edgeTolerance: -10 },
			    //pointLabels: {show: true}
        },

        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.

		series:[{label:'Fail'},{label:'Pass'}],		
		//seriesColors: [ "#0F6700", "#9C0000"],	
		seriesColors: [ "#9C0000", "#0F6700"],			
			highlighter:
		        {
			  show: true,
			  sizeAdjust: 7.5,
			  tooltipContentEditor:tooltipContentEditor  //new code added to attempt to add legend name to mouse over tool tip
		        },
		     cursor:
		        {
			  show: false,
			  zoom: true,
			  showTooltip: true
			  
		        },			
			legend:
			{
				//labels: ticks,
				labels1:['Total', 'Last 7 Day`s', 'Yesterday', 'Today'],
				show: true,
				//location: 'e',
				//renderer: $.jqplot.EnhancedLegendRenderer,
				rendererOptions:
				{
				    // numberColumns: 10, 
				    // numberRows: 5,
				    // seriesToggle: 900,
				    // disableIEFading: true
				},
				/*marginTop: '100px',
				marginBottom: '100px',*/
				placement: 'outside'
			}, 
			
        axes: {
            // Use a category axis on the x axis and use our custom ticks.
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                ticks: ticks,
		
            },
            // Pad the y axis just a little so bars can get close to, but
            // not touch, the grid boundaries.  1.2 is the default padding.
            yaxis: {                
                tickOptions: {formatString: '%d'}
            }
        }
    });
	
	FinalDay_pass=0;
	SevenDate_Pass=0;
	YDate_Pass=0;
	TodayDate_Pass=0;
	FinalDay_Fail=0;
	SevenDate_Fail=0;
	YDate_Fail=0;
	TodayDate_Fail=0;
		
   }
   
  
   // function parsemultipleSuiteLevelXML(xmlFile, doc) 
// {
	
	// var the_doc = doc;
	// the_doc.async=false;
	// ExeDateOrTime="";
	// //code for to parce day date in to table
	// var xml_files_array = new Array();
	// var dateFileList=new Array();
	 // var resultFile = XML_PATH + "TestResultList.xml";
	// Datatext="Test Executions For The Day :"+glbDate;
	// document.getElementById("tabletitle").text=Datatext;
	// the_doc.load(resultFile);	
	// var rootNode = listDoc.getElementsByTagName("ResultList");	
	// var fileNames = rootNode[0].getElementsByTagName("Result");	
	
	// // var control = document.getElementById('lnktable1notlink');
	// // control.style.visibility = "hidden";
	
	// // var control = document.getElementById('lnktablespell');
	// // control.style.visibility = "hidden";
	
	
	
	// // for(var v = 0; v < fileNames.length; v++) 
	// // {
			// // var fileName = "";
			// // fileName = fileNames[v];
			// // xmlFileName = getAttr(fileName, "file");
			// // var n = xmlFileName.search(xmlFile);
			// // if(n>0)
			// // {
				// // xml_files_array.push(xmlFileName);
			// // }
	// // }
	
	
		// for(var v = fileNames.length-1; v>=0; v--) 
	// {
		// var fileName = "";
			// fileName = fileNames[v];
		// xmlFileName = getAttr(fileName, "file");
			// var n = xmlFileName.search(xmlFile);
			// if(n>0)
			// {
				// xml_files_array.push(xmlFileName);
			// }
	// }
	
    // for(var c = 0; c<xml_files_array.length;c++)	
   // {
        // var XmlFilearr="./xml/"+xml_files_array[c];
	   // the_doc.load(XmlFilearr);
	
	// var testResults = the_doc.getElementsByTagName("Environment");

	// var environmentDetails = testResults[0];
	// //var category = getAttr(environmentDetails, "category");
	// var oS = getAttr(environmentDetails, "os");
	// var hostname = getAttr(environmentDetails, "hostname");
	// var overallStartTime = getAttr(environmentDetails, "startTime");
	// //alert(overallStartTime);
	// var overallStartTime1=overallStartTime.split(" ");
	
	// var overallEndTime = getAttr(environmentDetails, "endTime");
	// var overallExecutionTime = getAttr(environmentDetails, "totalExecutionDuration");
		
	// var logFile = getAttr(environmentDetails, "logFile");
  	// logFile = logFile.replace("//", "/");
	
	

// //document.getElementById('os_info').innerHTML = oS;
	// //document.getElementById('host_info').innerHTML = hostname;
	// //document.getElementById("start_time").innerHTML = overallStartTime;
	// //document.getElementById("end_time").innerHTML = overallEndTime;
	// //document.getElementById("total_execution_duration").innerHTML = overallExecutionTime;

	// testSuites = the_doc.getElementsByTagName("TestSuite");
	
	// var cumPassCount = 0;
	// var cumFailCount = 0;
	// var cumTotalCount = 0;
	// for(var i = 0; i < testSuites.length; i++) {
	
		// var passCount = 0;
		// var failCount = 0;
		// var totalCount = 0;
		// var testSuite = testSuites[i];
		
		// var _suiteName = getAttr(testSuite, "Name");	
		// suiteName = parseSuiteName(_suiteName);
		// var category = getAttr(testSuite, "Category");	
		// var executionEngine = getAttr(testSuite, "ExecutionEngine");	
		// var totalExecutionTime = getAttr(testSuite, "executionTime");
		// var browser = getAttr(testSuite, "browser");
		// var BaseUrl = getAttr(testSuite, "BaseUrl");
		// var tests = testSuite.getElementsByTagName("Test");
		
	// //	if(tests.length > 0 ) {
		// //	document.getElementById("no_files_found").style.display='none';
		// //}

		// for(var v = 0; v < tests.length; v++) {
			// //alert(tests.length);
			
			// var test= tests[v];
			// var isPassed = PASS;
			// var resultTag = test.getElementsByTagName("Result");
			// var allSteps = test.getElementsByTagName("Steps");
			// var steps = allSteps[0].getElementsByTagName("Step");
			
			// isPassed = getNodeValue(resultTag[0]);
			// if(isPassed.toLowerCase() == PASS.toLowerCase()) {
				// passCount = parseInt(passCount) + 1;
			// }
			// else {
				// failCount = parseInt(failCount) + 1;
			// }

			// totalCount = totalCount + 1;
		// }
		// cumPassCount = parseInt(cumPassCount) + parseInt(passCount);
	    // cumFailCount = parseInt(cumFailCount) + parseInt(failCount);
	    // cumTotalCount = parseInt(cumTotalCount) + parseInt(totalCount);
		


		// browser=textTag(browser);
		// BaseUrl=textTag(BaseUrl);
		// totalExecutionTime = textTag(totalExecutionTime);
		// category=textTag(category);
		// executionEngine=textTag(executionEngine);
		// //overallStartTime1=textTag(overallStartTime);

	
	// }
	
			// DatecumPassCount=DatecumPassCount+cumPassCount;
			// DatecumFailCount=DatecumFailCount+cumFailCount;
			
		// var params = "apps=" + _suiteName.toLowerCase() + "&browser=" + browser + "&baseurl=" +escape(BaseUrl) + "&xml=" + "."+XmlFilearr;
// //		alert("File"+XmlFilearr);
		// var allParams = params + "&resultType=all";
		// var passParams = params + "&resultType=pass";
		// var failParams = params + "&resultType=fail";
		
		// var aTag = constructHrefTag1("", overallStartTime1[1], allParams);
		// aTag.className = 'view';
	// //	aTag.onclick=suitelevelxml(XmlFilearr);
		
		// if(cumPassCount > 0){
			// //cumPassCount = constructHrefTag(TestsuiteHTML, cumPassCount, passParams);
		// cumPassCount = textTag(cumPassCount);
			// //cumPassCount.className = "pass";(RK)
		// }
		// else {
			// cumPassCount = textTag(cumPassCount);
		// }
		
		// if(cumFailCount  > 0){
			// //cumFailCount = constructHrefTag(TestsuiteHTML, cumFailCount, failParams);
			// cumFailCount=textTag(cumFailCount);
			// //cumFailCount.className = 'fail';(RK)
			
		// }
		// else {
			// cumFailCount = textTag(cumFailCount);
		// }

		// totalCount = constructHrefTag(TestsuiteHTML,totalCount,allParams);	
					// var linkflag=0;
					// var spellflag=0;
					// EroorRoot="./xml/ErrorResults.xml"
				// var pieces = XmlFilearr.split(/[\s/]+/);
				// var file1= pieces[pieces.length-1]
				// var splErrFile=XmlFilearr.replace(file1,"SpellErrors_"+file1);	
				// var linkErrFile=XmlFilearr.replace(file1,"LinkErrors_"+file1);
				
				// var the_doc_Errorlink = CreateXMLObject();
				// the_doc_Errorlink.async=false;
				// the_doc_Errorlink.load(EroorRoot);	
				// Spellfiles = the_doc_Errorlink.getElementsByTagName("SpellError");	
				// for(var u = 0; u < Spellfiles.length; u++)
				// {
					// var spellfile= Spellfiles[u];
					// var _splfile = getAttr(spellfile, "file");
					// if (splErrFile.search(_splfile)>0)
					// {
						// spellflag=1;
						// break;
					// }
				// }
				// linkfiles = the_doc_Errorlink.getElementsByTagName("LinkError");		
				// for(var n = 0; n < linkfiles.length; n++)
				// {
					// var linkfile= linkfiles[n];
					// var _lnkfile = getAttr(linkfile, "file");			
					// if (linkErrFile.search(_lnkfile)>0)
					// {
						// linkflag=1;
						// break;
					// }
			
				// }				
			
					 			// // Full Report View link
			// var Params_spell_View = "<img src=\"SpellError.png\"/>";
			// path="../testReports/images/SpellError.png"
			// var Params_spell_Viewtag = Params_spell_Viewtag = textTag(Params_spell_View);
            // var params1 = "apps=" + _suiteName.toLowerCase() + "&browser=" + browser + "&baseurl=" +escape(BaseUrl) + "&xml=" + "."+splErrFile;
			// Params_spell_Viewtag = constructHrefTagViewspell(TestErrorSuiteHTML, Params_spell_View, params1,path);				
			// var Params_spell_View = "<img src=\"linkerror.png\"/>";
			// path="../testReports/images/linkerror.png"
			// var Params_link_Viewtag = Params_link_Viewtag = textTag(Params_spell_View);
            // var params2 = "apps=" + _suiteName.toLowerCase() + "&browser=" + browser + "&baseurl=" +escape(BaseUrl) + "&xml=" + "."+linkErrFile;
			// Params_link_Viewtag = constructHrefTagViewspell(TestErrorSuiteLinkHTML, Params_spell_View, params2,path);
			// //var flagspl=0;
		// overallExecutionTime = textTag(overallExecutionTime);
		// var tableRow = new TableRow();
			
		// tableRow.className='td';
		// tableRow.createLastRow();
		
		// tableRow.insertRecord(aTag);
		// //tableRow.insertRecord(category);
		// //alert(overallStartTime);
		// //tableRow.insertRecord(overallStartTime1);
		// //tableRow.insertRecord(executionEngine);
	// //	tableRow.insertRecord(browser);
		// //tableRow.insertRecord(BaseUrl);
		// tableRow.insertRecord(cumPassCount);
		
		// tableRow.insertRecord(cumFailCount);
		// //tableRow.insertRecord(totalCount);
		// //tableRow.insertRecord(totalExecutionTime);
		// tableRow.insertRecord(overallExecutionTime);
		// tableRow.insertRecord(imageTag(logFile,"log_file_icon.png"));	
		// if(spellflag==1)
		// {
			// tableRow.insertRecord(Params_link_Viewtag);
			// spellflag=0;
			// var flagspl=1;
		// }
	// else
	// {
		// tableRow.insertRecord(textTag(""));
		// }
		// if(linkflag==1)
		// {
			// tableRow.insertRecord(Params_spell_Viewtag);
			// linkflag=0;
			// var flagspl=1;
		// }
		// else
		// {
			// tableRow.insertRecord(textTag(""));
		// }
		
		
		


// }
    
		// // if(flagspl==1) 
		// // {
		// // var control = document.getElementById('lnktable1notlink');
		
	    // // //control.style.visibility = "visible";
		// // }
		// // else
		// // {
		// // var control = document.getElementById('lnktablespell');
	    // // //control.style.visibility = "visible";		
		// // }
	// //document.getElementById('totPass').innerHTML = cumPassCount;
	// //document.getElementById('totFail').innerHTML = cumFailCount;
	// //document.getElementById('total').innerHTML = cumTotalCount;
// //	alert("cumPassCount["+cumPassCount+"]" +" , " +"cumFailCount["+cumFailCount+"]" +","+"cumTotalCount["+cumTotalCount+"]" );
	
	
	
  // //ExeDateOrTime="TestExecution Stastus for the day '"+glbDate+"'"
 // // google.load("visualization", "1", {packages:["corechart"]})
 // // google.setOnLoadCallback(drawChart);
  
// //	google.load("visualization", "1", {packages:["corechart"]})
	// //document.getElementById('pie_chart').innerHTML="";	
	// drawChart(Datatext);
	// TrendDataBarcharts_Sets();
	// drawTestSetBarChart();
	// drawTestSetBarChart1();
	// //drawTestSetBarChart1();
	// //google.setOnLoadCallback(drawTestSetBarChart);
	// //google.setOnLoadCallback(drawTestSetBarChart1);
	// TrendDataForBarcharts();
	// drawTestProcessBarChart();
	

// }


	function parsemultipleSuiteLevelXML(xmlFile, doc) 
	{

		var the_doc = doc;
		the_doc.async=false;
		ExeDateOrTime="";
		var flagspl=0;
		//code for to parce day date in to table
		var xml_files_array = new Array();
		var dateFileList=new Array();
		var resultFile = XML_PATH + "TestResultList.xml";
		Datatext="Test Executions For The Day :"+glbDate;
		document.getElementById("tabletitle").text=Datatext;
		the_doc.load(resultFile);	
		var rootNode = listDoc.getElementsByTagName("ResultList");	
		var fileNames = rootNode[0].getElementsByTagName("Result");	

		var control = document.getElementById('lnktable1notlink');
		control.style.display = "none";

		var control = document.getElementById('lnktablespell');
		control.style.display = "none";
		
		
		var aTagarr=new Array();
		var cumPassCountarr= new Array();
		var cumFailCountarr=new Array();
		var overallExecutionTimearr=new Array();
		var logFilearr=new Array();
		var Params_link_Viewtagarr=new Array();
		var Params_spell_Viewtagarr=new Array();
		

		for(var v = fileNames.length-1; v>=0; v--) 
		{
			var fileName = "";
			fileName = fileNames[v];
			xmlFileName = getAttr(fileName, "file");
			var n = xmlFileName.search(xmlFile);
			if(n>0)
			{
				xml_files_array.push(xmlFileName);
			}
		}

		for(var c = 0; c<xml_files_array.length;c++)	
		{
			var XmlFilearr="./xml/"+xml_files_array[c];
			the_doc.load(XmlFilearr);
			var testResults = the_doc.getElementsByTagName("Environment");
			var environmentDetails = testResults[0];
			//var category = getAttr(environmentDetails, "category");
			var oS = getAttr(environmentDetails, "os");
			var hostname = getAttr(environmentDetails, "hostname");
			var overallStartTime = getAttr(environmentDetails, "startTime");
			//alert(overallStartTime);
			var overallStartTime1=overallStartTime.split(" ");
			var overallEndTime = getAttr(environmentDetails, "endTime");
			var overallExecutionTime = getAttr(environmentDetails, "totalExecutionDuration");
			var logFile = getAttr(environmentDetails, "logFile");
			logFile = logFile.replace("//", "/");
			testSuites = the_doc.getElementsByTagName("TestSuite");
			var cumPassCount = 0;
			var cumFailCount = 0;
			var cumTotalCount = 0;
			for(var i = 0; i < testSuites.length; i++) {
				var passCount = 0;
				var failCount = 0;
				var totalCount = 0;
				var testSuite = testSuites[i];
				var _suiteName = getAttr(testSuite, "Name");	
				suiteName = parseSuiteName(_suiteName);
				var category = getAttr(testSuite, "Category");	
				var executionEngine = getAttr(testSuite, "ExecutionEngine");	
				var totalExecutionTime = getAttr(testSuite, "executionTime");
				var browser = getAttr(testSuite, "browser");
				var BaseUrl = getAttr(testSuite, "BaseUrl");
				var tests = testSuite.getElementsByTagName("Test");
				for(var v = 0; v < tests.length; v++) {	
					var test= tests[v];
					var isPassed = PASS;
					var isFailed = FAIL;
					var resultTag = test.getElementsByTagName("Result");
					var allSteps = test.getElementsByTagName("Steps");
					var steps = allSteps[0].getElementsByTagName("Step");
					isPassed = getNodeValue(resultTag[0]);
					isFailed = getNodeValue(resultTag[0]);
					if(isPassed.toLowerCase() == PASS.toLowerCase()) {
						passCount = parseInt(passCount) + 1;
						}
					else if (isFailed.toLowerCase() == FAIL.toLowerCase()){
						failCount = parseInt(failCount) + 1;
						}
					else{
						passCount = parseInt(passCount) + 1;
					}

					totalCount = totalCount + 1;
				}
				cumPassCount = parseInt(cumPassCount) + parseInt(passCount);
				cumFailCount = parseInt(cumFailCount) + parseInt(failCount);
				cumTotalCount = parseInt(cumTotalCount) + parseInt(totalCount);
				browser=textTag(browser);
				BaseUrl=textTag(BaseUrl);
				totalExecutionTime = textTag(totalExecutionTime);
				category=textTag(category);
				executionEngine=textTag(executionEngine);			
			}
			DatecumPassCount=DatecumPassCount+cumPassCount;
			DatecumFailCount=DatecumFailCount+cumFailCount;
			var params = "apps=" + _suiteName.toLowerCase() + "&browser=" + browser + "&baseurl=" +escape(BaseUrl) + "&xml=" + "."+XmlFilearr;
			var allParams = params + "&resultType=all";
			var passParams = params + "&resultType=pass";
			var failParams = params + "&resultType=fail";
			var aTag = constructHrefTag1("", overallStartTime1[1], allParams);
			aTag.className = 'view';
			if(cumPassCount > 0){		
				cumPassCount = textTag(cumPassCount);					
			}
			else {
				cumPassCount = textTag(cumPassCount);
			}

			if(cumFailCount  > 0){			
			cumFailCount=textTag(cumFailCount);			
			}
			else {
			cumFailCount = textTag(cumFailCount);
			}
			totalCount = constructHrefTag(TestsuiteHTML,totalCount,allParams);	
			var linkflag=0;
			var spellflag=0;
			EroorRoot="./xml/ErrorResults.xml"
			var pieces = XmlFilearr.split(/[\s/]+/);
			var file1= pieces[pieces.length-1]
			var splErrFile=XmlFilearr.replace(file1,"SpellErrors_"+file1);	
			var linkErrFile=XmlFilearr.replace(file1,"LinkErrors_"+file1);
			var the_doc_Errorlink = CreateXMLObject();
			the_doc_Errorlink.async=false;
			the_doc_Errorlink.load(EroorRoot);	
			Spellfiles = the_doc_Errorlink.getElementsByTagName("SpellError");	
			for(var u = 0; u < Spellfiles.length; u++)
			{
			var spellfile= Spellfiles[u];
			var _splfile = getAttr(spellfile, "file");
			if (splErrFile.search(_splfile)>0)
			{
			spellflag=1;
			flagspl=1;
			break;
			}
			}
			linkfiles = the_doc_Errorlink.getElementsByTagName("LinkError");		
			for(var n = 0; n < linkfiles.length; n++)
			{
			var linkfile= linkfiles[n];
			var _lnkfile = getAttr(linkfile, "file");			
			if (linkErrFile.search(_lnkfile)>0)
			{
			linkflag=1;
			flagspl=1;
			break;
			}

			}				

			// Full Report View link
			var Params_spell_View = "<img src=\"SpellError.png\"/>";
			path="../testReports/images/SpellError.png"
			var Params_spell_Viewtag = Params_spell_Viewtag = textTag(Params_spell_View);
			var params1 = "apps=" + _suiteName.toLowerCase() + "&browser=" + browser + "&baseurl=" +escape(BaseUrl) + "&xml=" + "."+splErrFile;
			Params_spell_Viewtag = constructHrefTagViewspell(TestErrorSuiteHTML, Params_spell_View, params1,path);				
			var Params_spell_View = "<img src=\"linkerror.png\"/>";
			path="../testReports/images/linkerror.png"
			var Params_link_Viewtag = Params_link_Viewtag = textTag(Params_spell_View);
			var params2 = "apps=" + _suiteName.toLowerCase() + "&browser=" + browser + "&baseurl=" +escape(BaseUrl) + "&xml=" + "."+linkErrFile;
			Params_link_Viewtag = constructHrefTagViewspell(TestErrorSuiteLinkHTML, Params_spell_View, params2,path);

			overallExecutionTime = textTag(overallExecutionTime);
			
			aTagarr.push(aTag);
			cumPassCountarr.push(cumPassCount);
			cumFailCountarr.push(cumFailCount);
			overallExecutionTimearr.push(overallExecutionTime);
			logFilearr.push(logFile);
			if(spellflag==1)
			{
			Params_link_Viewtagarr.push(Params_link_Viewtag);
			spellflag=0;
			var flagspl=1;
			}
			else
			{
			Params_link_Viewtagarr.push("");
			}
			if(linkflag==1)
			{
			Params_spell_Viewtagarr.push(Params_spell_Viewtag);
			linkflag=0;
			var flagspl=1;
			}
			else
			{
			Params_spell_Viewtagarr.push("");
			}
			
			
			
			
			
			// var tableRow = new TableRow();			
			// tableRow.className='td';
			// tableRow.createLastRow();		
			// tableRow.insertRecord(aTag);
			// tableRow.insertRecord(cumPassCount);		
			// tableRow.insertRecord(cumFailCount);
			// tableRow.insertRecord(overallExecutionTime);
			// tableRow.insertRecord(imageTag(logFile,"log_file_icon.png"));	
			// if(spellflag==1)
			// {
			// tableRow.insertRecord(Params_link_Viewtag);
			// spellflag=0;
			// var flagspl=1;
			// }
			// //		else
			// //	{
			// //	tableRow.insertRecord(textTag(""));
			// //	}
			// if(linkflag==1)
			// {
			// tableRow.insertRecord(Params_spell_Viewtag);
			// linkflag=0;
			// var flagspl=1;
			// }
			// //	else
			// //	{
			// //		tableRow.insertRecord(textTag(""));
			// //	}
			// //tableRow.insertRecord(overallExecutionTime);
		}
		
		if(flagspl==1) 
		{
		var control = document.getElementById('lnktable1notlink');
		control.style.display = "block";
		}
		else
		{
		var control = document.getElementById('lnktablespell');
		control.style.display = "block";		
		}

			
		for(h=0;h<aTagarr.length;h++)
		{
			var tableRow = new TableRow();			
			tableRow.className='td';
			tableRow.createLastRow();
			tableRow.insertRecord(aTagarr[h]);
			tableRow.insertRecord(cumPassCountarr[h]);		
			tableRow.insertRecord(cumFailCountarr[h]);
			tableRow.insertRecord(overallExecutionTimearr[h]);
			tableRow.insertRecord(imageTag(logFilearr[h],"log_file_icon.png"));	
			if(flagspl==1)
			{
				if(Params_link_Viewtagarr[h]!="")
				{
				tableRow.insertRecord(Params_link_Viewtagarr[h]);
				//tableRow.insertRecord(textTag(""));
				}
				else
				{
				//tableRow.insertRecord(Params_link_Viewtagarr[h]);
				tableRow.insertRecord(textTag(""));

				}	
			}

			if(flagspl==1)
			{
				if(Params_spell_Viewtagarr[h]!="")
				{
				tableRow.insertRecord(Params_spell_Viewtagarr[h]);
				//tableRow.insertRecord(textTag(""));
				}
				else
				{
					//tableRow.insertRecord(Params_spell_Viewtagarr[h]);
					tableRow.insertRecord(textTag(""));
				}
			}

		}
		drawChart(Datatext);
		TrendDataBarcharts_Sets();
		drawTestSetBarChart();
		drawTestSetBarChart1();
		TrendDataForBarcharts();
		drawTestProcessBarChart();
		

		
	}


function constructHrefTag1(linkName, the_name,params) { 
/*	var aTag = document.createElement('a');

	aTag.name = 'a_' + the_name;
	aTag.id = 'a_' + the_name;
	url = linkName;
	if(params != ""){
		url = url + "?" + params;
	}
	
	aTag.href= url;	
	aTag.appendChild(document.createTextNode(the_name));
*/  var aTag = document.createElement('a');
	aTag.name = 'a_' + the_name;
	//aTag.addEventListener('click', testFunction, true);
	  
		if (system_browser_name != "MSIE")
	  {
		aTag.addEventListener('click', function(event) { testFunction(params)}, true);
	  
	  }
	  //testuite_link_tag.setAttribute('onclick', display_testscenario_report(test_suite_id,report_dt));

  	  if (system_browser_name == "MSIE")
	  {
	  
		aTag.attachEvent('onclick', function(event) { testFunction(params)}, true);
	  }
	
	

	
	aTag.appendChild(document.createTextNode(the_name));
	return aTag;
}

function getXmlfile(params) {
	var theArray = params.split("&");
	

	for(var i=0; i<theArray.length; i++) {
	var theArray2=theArray[i].split("=");
	 for (var j=0;j<theArray2.length; j++)
            if(theArray2[j]=="xml")
			{
			 xml=theArray2[j+1];
			 break;
			}
		}
			return xml;
	}




function testFunction(params)
{	
	
	var the_xmlFileName = getXmlfile(params);
	
	suitelevelxml(the_xmlFileName);
	document.getElementById('lnktable').style.display="inline";
	document.getElementById('lnktable1').style.display="inline";
}



function TableRow1() {
	// creates a table object

	this.tbl = document.getElementById('tblSample1');
	this.position = 0;
	this.row;
	this.data;
	this.failColor = "#FF0000";
	this.passColor = "#1E762D";

	this.createLastRow = function()
				{
					var lastRow = this.tbl.rows.length; 
					var row = this.tbl.insertRow(lastRow);
				 	this.row = row;
				}

	this.insertRecord = function(data)
				{
					var one = this.row.insertCell(this.position);
					one.appendChild(data);
					this.position++;
				}

	this.applyColor = function(color)
				{
					this.row.className = 'pass';
					if(color.toLowerCase() == "skip"){
						this.row.className = 'info';
					}
					if(color.toLowerCase() == "fail"){
						this.row.className = 'fail';
					}

				}
}


function suitelevelxml(xmlFile) {
	//alert(xmlFile);
     ExeDateOrTime="";
	 var table = document.getElementById("tblSample1");  
	 var rowCount = table.rows.length; 

	 for(var i=rowCount; i>0; i--) 
	 {
		 table.deleteRow(i-1);
	 }
	 

			var listDoc = null;
			listDoc = CreateXMLObject();
             
			//parseResultList(listDoc, the_xmlFileName);
			//var the_xmlFile = XML_PATHTTEMP +"TestDataResultList.xml";								
			var the_doc = listDoc;
			the_doc.async=false;
			//alert("Filexml"+xmlFile);
			xmlFile=xmlFile.replace("..",".");
			//alert("Filexm2"+xmlFile);
			the_doc.load(xmlFile);
          
	

	var testResults = the_doc.getElementsByTagName("Environment");

	var environmentDetails = testResults[0];
	//var category = getAttr(environmentDetails, "category");
	var oS = getAttr(environmentDetails, "os");
	var hostname = getAttr(environmentDetails, "hostname");
	var overallStartTime = getAttr(environmentDetails, "startTime");
	var overallEndTime = getAttr(environmentDetails, "endTime");
	var overallExecutionTime = getAttr(environmentDetails, "totalExecutionDuration");
	var overallStartTime1=overallStartTime.split(" ");	
	var logFile = getAttr(environmentDetails, "logFile");
  	logFile = logFile.replace("//", "/");
	
	//alert(overallStartTime);
	var overallStartTime1=overallStartTime.split(" ");

	//document.getElementById('os_info').innerHTML = oS;
	//document.getElementById('host_info').innerHTML = hostname;
	//document.getElementById("start_time").innerHTML = overallStartTime;
	//document.getElementById("end_time").innerHTML = overallEndTime;
	//document.getElementById("total_execution_duration").innerHTML = overallExecutionTime;


	testSuites = the_doc.getElementsByTagName("TestSuite");
	
	var cumPassCount = 0;
	var cumFailCount = 0;
	var cumTotalCount = 0;
	DatecumPassCount=0;
	DatecumFailCount=0;
	for(var i = 0; i < testSuites.length; i++) {
		var passCount = 0;
		var failCount = 0;
		var totalCount = 0;
		var testSuite = testSuites[i];
		
		var _suiteName = getAttr(testSuite, "Name");	
		suiteName = parseSuiteName(_suiteName);
		var category = getAttr(testSuite, "Category");	
		var executionEngine = getAttr(testSuite, "ExecutionEngine");	
		var totalExecutionTime = getAttr(testSuite, "executionTime");
		var browser = getAttr(testSuite, "browser");
		var BaseUrl = getAttr(testSuite, "BaseUrl");
		var tests = testSuite.getElementsByTagName("Test");
		
		if(tests.length > 0 ) {
			//document.getElementById("no_files_found").style.display='none';
		}
		//alert("length"+tests.length);
		
		for(var v = 0; v < tests.length; v++) {	
			
			
			var test= tests[v];
			var isPassed = PASS;
			var isFailed = FAIL;
			var resultTag = test.getElementsByTagName("Result");			
			var allSteps = test.getElementsByTagName("Steps");
			var steps = allSteps[0].getElementsByTagName("Step");			
			isPassed = getNodeValue(resultTag[0]);		
			isFailed = getNodeValue(resultTag[0]);		
			if(isPassed.toLowerCase() == PASS.toLowerCase()) {
				passCount = parseInt(passCount) + 1;
			}
			else if (isFailed.toLowerCase() == FAIL.toLowerCase()){
				failCount = parseInt(failCount) + 1;
			}
			else
			{
				passCount = parseInt(passCount) + 1;
			}
		totalCount = totalCount + 1;
		}
		cumPassCount = parseInt(cumPassCount) + parseInt(passCount);
	    cumFailCount = parseInt(cumFailCount) + parseInt(failCount);
	    cumTotalCount = parseInt(cumTotalCount) + parseInt(totalCount);
		var params = "apps=" + _suiteName.toLowerCase() + "&browser=" + browser + "&baseurl=" +escape(BaseUrl) + "&xml=" + "."+xmlFile;
		var allParams = params + "&resultType=all";
		var passParams = params + "&resultType=pass";
		var failParams = params + "&resultType=fail";
		var aTag = constructHrefTag(TestsuiteHTML, suiteName, allParams);
		
	aTag.className = 'view';
	if(passCount > 0){
			passCount = constructHrefTag(TestsuiteHTML, passCount, passParams);

			passCount.className = 'pass';
		}
		else {
			passCount = textTag(passCount);
		}
	if(failCount  > 0){
			failCount = constructHrefTag(TestsuiteHTML, failCount, failParams);
			failCount.className = 'fail';
		}
		else {
			failCount = textTag(failCount);
		}
		totalCount = constructHrefTag(TestsuiteHTML,totalCount,allParams);	
		browser=textTag(browser);
		BaseUrl=textTag(BaseUrl);
		totalExecutionTime = textTag(totalExecutionTime);
		category=textTag(category);
		executionEngine=textTag(executionEngine);
		var tableRow1 = new TableRow1();
		tableRow1.className='td';
		tableRow1.createLastRow();
		tableRow1.insertRecord(aTag);		
		tableRow1.insertRecord(totalExecutionTime);
		tableRow1.insertRecord(browser);
		tableRow1.insertRecord(executionEngine);
		tableRow1.insertRecord(passCount);		
		tableRow1.insertRecord(failCount);

		
	}
	          ExeDateOrTime="Test sets Executed at '"+glbDate+" "+overallStartTime1[1]+"'"
		     DatecumPassCount=DatecumPassCount+cumPassCount;					
			DatecumFailCount=DatecumFailCount+cumFailCount;

			var rows = table.getElementsByTagName("tr");
			//alert('rows='+rows.length);			
			for(var i=0; i<rows.length; i++ ){
				if(i % 2 == 0){
						rows[i].className = "even";			
				}else{
						rows[i].className = "odd";								
				}
		    }
	Datatext="Test Sets Executed at "+overallStartTime1[1];
	document.getElementById('pie_chart').innerHTML="";	
	document.getElementById("tablesettitle").textContent="Test Set Execution Status At:"+overallStartTime1[1];
	drawChart(Datatext);
}

function timeToMillisecs(time)
{
   var sum = 0;
   var t = time.split(',');
   for( i=0; i<t.length; i++) {
      var arr = t[i].split(':');
      var min = arr[0] * 60000;
      var secs = arr[1] * 1000;
      var ms = arr[2] * 1;

      sum += min + secs + ms;
   }
  return sum;
}

function msToTime(duration) {
    var milliseconds = parseInt((duration%1000))
        , seconds = parseInt((duration/1000)%60)
        , minutes = parseInt((duration/(1000*60))%60)
        , hours = parseInt((duration/(1000*60*60))%24);

    hours = (hours < 10) ? "0" + hours : hours;
    minutes = (minutes < 10) ? "0" + minutes : minutes;
    seconds = (seconds < 10) ? "0" + seconds : seconds;

    return hours + ":" + minutes + ":" + seconds + ":" + milliseconds;
}


function  parseMultimediaerrorXML(appName,test,the_xmlFileName)
{

	var listDoc = null;
	listDoc = CreateXMLObject();
	var xmlFile =the_xmlFileName;		
	var the_doc = listDoc;
	the_doc.async=false;
	the_doc.load(xmlFile);
	var xml_files_array = new Array();
	var dateFileList=new Array();
	var rootNode = listDoc.getElementsByTagName("TestSet");	
	
    var childCount=rootNode[0].childElementCount;
	//alert(system_browser_name);
	
	rootNode[0].children;
	for(var i=0; i<rootNode.length; i++ ){		
		 var testSuite = rootNode[i]; 
		 var _suiteName = getAttr(testSuite,"name");
		  if(_suiteName.toLowerCase() != appName.toLowerCase()){
			continue;
		  }
		  var tests = testSuite.getElementsByTagName("TestScenario");
		  
		 for(var n = 0; n < tests.length; n++) {
		  var t=tests[n]
		  var TestScenarioid = getAttr(t,"id");
		  
		  if(TestScenarioid.toLowerCase() != test.toLowerCase()){
			continue;
		  }
		  var ErrorPages = tests[n].getElementsByTagName("ErrorPage");
		  for(var v = 0; v < ErrorPages.length; v++) {
		  var ErrorPage= ErrorPages[v];
		  var _page = getAttr(ErrorPage,"page");
		  Errpagechildcount=ErrorPages[v].childElementCount;
			for(var k = 0; k< Errpagechildcount; k++) {
		      var Nodename1=ErrorPages[v].children[k].nodeName;
			  var ErrorCount=ErrorPages[v].children[k].childNodes.length;
			 //var Errornumber=ErrorPages[v].children[k].childNodes.childElementCount
			  for(var z = 0; z< ErrorCount; z++) {
			  
                // var Cdate=ErrorPages[v].children[k].childNodes[z].textContent;	


				// var tableRow1 = new TableRow1();
				// tableRow1.className='td';			
				// tableRow1.createLastRow();
				// //tableRow1.insertRecord(aTag);
				
				// tableRow1.insertRecord(textTag(_suiteName));
				// tableRow1.insertRecord(textTag(TestScenarioid));
				// tableRow1.insertRecord(textTag(_page));
				// tableRow1.insertRecord(textTag(Nodename1));
				// tableRow1.insertRecord(textTag(Cdate));
				
				
				var errorurl="";
				var ErrorDes="";
                var Cdata=ErrorPages[v].children[k].childNodes[z].textContent;	
				errorurl=Cdata;
				if(xmlFile.search("LinkErrors")==0||Nodename1.search("Exceptions")==0)
				{
				dateFileList=Cdata.split("||");
				var errorurl=dateFileList[0];
				var ErrorDes=dateFileList[1];
				}
 				if(xmlFile.search("LinkErrors")==0||Nodename1.search("Error404")==0)
				{
				errorurl=Cdata;
				ErrorDes="Page not Found";
				} 
 				if(xmlFile.search("LinkErrors")==0||Nodename1.search("Error500")==0)
				{
				errorurl=Cdata;
				ErrorDes="Internal Server Error";
				}  				
                if (Nodename1.search("GrammarError")==0)
				{
				Nodename1="Grammar";
				}
			   if (Nodename1.search("SpellError")==0)
				{
				Nodename1="Spelling";
				}
				if (Nodename1.search("Error404")==0)
				{
				Nodename1="404";
				}
				var tableRow1 = new TableRow1();
				tableRow1.className='td';
				tableRow1.createLastRow();
				//tableRow1.insertRecord(aTag);
				
				tableRow1.insertRecord(textTag(_suiteName));
				tableRow1.insertRecord(textTag(_page));
				tableRow1.insertRecord(textTag(Nodename1));
				tableRow1.insertRecord(textTag(errorurl));
				if(xmlFile.search("SpellErrors")!=0)
				 {
					tableRow1.insertRecord(textTag(ErrorDes));
		         }
				
				
				
		
				
			  }
			  
			}
		}
	//alert(xml_files_array);
	}
	

	}

		for(var i=0; i<rows.length; i++ ){
			if(i % 2 == 0){
				if(rows[i].className.toLowerCase() == "pass")
					rows[i].className = "evenPass";			
				else
					rows[i].className = "evenFail";			
			}else{
				if(rows[i].className.toLowerCase() == "pass")
					rows[i].className = "oddPass";			
				else
					rows[i].className = "oddFail";	
			}
		}
	
}

function  parseMultimediaerrorXML_spellTotal(appName,test,the_xmlFileName)
{
var control = document.getElementById('lnktable1');
control.style.visibility = "hidden";
	var listDoc = null;
	listDoc = CreateXMLObject();
	var xmlFile =the_xmlFileName;		
	var the_doc = listDoc;
	the_doc.async=false;
	the_doc.load(xmlFile);
	var xml_files_array = new Array();
	var dateFileList=new Array();
	var rootNode = listDoc.getElementsByTagName("TestSet");	
	
    var childCount=rootNode[0].childElementCount;
	//alert(system_browser_name);
	
	rootNode[0].children;
	for(var i=0; i<rootNode.length; i++ ){		
		 var testSuite = rootNode[i]; 
		 var _suiteName = getAttr(testSuite,"name");
		 var tests = testSuite.getElementsByTagName("TestScenario");
		  if(_suiteName.toLowerCase() != appName.toLowerCase()){
			continue;
		  }
		  var tests = testSuite.getElementsByTagName("TestScenario");

		  
		for(var n = 0; n < tests.length; n++) {
		  var t=tests[n]
		  var TestScenarioid = getAttr(t,"id");
		  if(TestScenarioid.toLowerCase() != test.toLowerCase()){
			continue;
		  }
		  var ErrorPages = tests[n].getElementsByTagName("ErrorPage");
		  for(var v = 0; v < ErrorPages.length; v++) {
		  var ErrorPage= ErrorPages[v];
		  var _page = getAttr(ErrorPage,"page");
		  Errpagechildcount=ErrorPages[v].childElementCount;

		  
			 //var Errornumber=ErrorPages[v].children[k].childNodes.childElementCount
			  //for(var z = 0; z< ErrorCount; z++) {
			  
                //var Cdate=ErrorPages[v].children[k].childNodes[z].textContent;	


				var tableRow = new TableRow();
				tableRow.className='td';
				tableRow.createLastRow();
				//tableRow1.insertRecord(aTag);
				
				tableRow.insertRecord(textTag(_suiteName));
				tableRow.insertRecord(textTag(TestScenarioid));
				tableRow.insertRecord(textTag(_page));

				
				for(var k = 0; k< Errpagechildcount; k++) {
					var Nodename1=ErrorPages[v].children[k].nodeName;
					var ErrorCount=ErrorPages[v].children[k].childNodes.length;				
					if(Nodename1=="GrammarErrors")
					{
					if(Errpagechildcount>1){
						tableRow.insertRecord(textTag(ErrorCount));
					}else{
						tableRow.insertRecord(textTag(0));
						tableRow.insertRecord(textTag(ErrorCount));
					}

					}
					else if(Nodename1=="SpellErrors"){
					if(Errpagechildcount>1){
						tableRow.insertRecord(textTag(ErrorCount));
					}else{
						tableRow.insertRecord(textTag(ErrorCount));
						tableRow.insertRecord(textTag(0));
					}
					}
					else 
					{
						tableRow.insertRecord(textTag("0"));
					}
				}
		
				
			  //}
			  
			
			}
		}
	//alert(xml_files_array);
	}

}


function  parseMultimediaerrorXML_LinkTotal(appName,test,the_xmlFileName)
{
var control = document.getElementById('lnktable1');
control.style.visibility = "hidden";
	var listDoc = null;
	listDoc = CreateXMLObject();
	var xmlFile =the_xmlFileName;		
	var the_doc = listDoc;
	the_doc.async=false;
	the_doc.load(xmlFile);
	var xml_files_array = new Array();
	var dateFileList=new Array();
	var rootNode = listDoc.getElementsByTagName("TestSet");	
	
    var childCount=rootNode[0].childElementCount;
	//alert(system_browser_name);
	
	rootNode[0].children;
	for(var i=0; i<rootNode.length; i++ ){		
		 var testSuite = rootNode[i]; 
		 var _suiteName = getAttr(testSuite,"name");
		 var tests = testSuite.getElementsByTagName("TestScenario");
		  if(_suiteName.toLowerCase() != appName.toLowerCase()){
			continue;
		  }
		  var tests = testSuite.getElementsByTagName("TestScenario");
		  for(var n = 0; n < tests.length; n++) {
		  var t=tests[n]
		  var TestScenarioid = getAttr(t,"id");
		  if(TestScenarioid.toLowerCase() != test.toLowerCase()){
			continue;
		  }
		  var ErrorPages = tests[n].getElementsByTagName("ErrorPage");
		  for(var v = 0; v < ErrorPages.length; v++) {
		  var ErrorPage= ErrorPages[v];
		  var _page = getAttr(ErrorPage,"page");
		  Errpagechildcount=ErrorPages[v].childElementCount;
			for(var k = 0; k< Errpagechildcount; k++) {
		      var Nodename1=ErrorPages[v].children[k].nodeName;
			  var ErrorCount=ErrorPages[v].children[k].childNodes.length;
			 //var Errornumber=ErrorPages[v].children[k].childNodes.childElementCount
			  //for(var z = 0; z< ErrorCount; z++) {
			  
                //var Cdate=ErrorPages[v].children[k].childNodes[z].textContent;	


				var tableRow = new TableRow();
				tableRow.className='td';
				tableRow.createLastRow();
				//tableRow1.insertRecord(aTag);
				
				tableRow.insertRecord(textTag(_suiteName));				
				tableRow.insertRecord(textTag(_page));
				if(Nodename1=="Exceptions"){
				tableRow.insertRecord(textTag(ErrorCount));
				}
				else{
				tableRow.insertRecord(textTag("0"));
				}
				if(Nodename1=="Error404"){
				tableRow.insertRecord(textTag(ErrorCount));
				}
				else{
				tableRow.insertRecord(textTag("0"));
				}
			  if(Nodename1=="Error505"){
				tableRow.insertRecord(textTag(ErrorCount));
				}
				else{
				tableRow.insertRecord(textTag("0"));
				}
				
			  //}
			  
			}
		}
	//alert(xml_files_array);
	}
	}

}

function  parseMultimediaerrorSuiteXML(appName,test,the_xmlFileName)
{
var control = document.getElementById('lnktable1');
control.style.visibility = "hidden";

	var listDoc = null;
	//var pieces = the_xmlFileName.split(/[\s/]+/);
	//var file1= pieces[pieces.length-1]
	//var splErrFile=the_xmlFileName.replace(file1,"SpellErrors_"+file1);	
	//var linkErrFile=the_xmlFileName.replace(file1,"LinkErrors_"+file1);
	
	var the_doc_spell = CreateXMLObject();
	var the_doc_link = CreateXMLObject();

	the_doc_spell.async=false;
    the_doc_spell.load(the_xmlFileName);
	
	//var the_doc_link=doc;
	//the_doc_link.async=false;
    //the_doc_link.load(linkErrFile);
	

	var rootNode = the_doc_spell.getElementsByTagName("TestSet");	
	
    var childCount=rootNode[0].childElementCount;
	rootNode[0].children;
	for(var i=0; i<rootNode.length; i++ ){		
		 var testSuite = rootNode[i]; 
		 var _suiteName = getAttr(testSuite,"name");
		 var _tests = testSuite.getElementsByTagName("TestScenario");
//		  if(_suiteName.toLowerCase() != appName.toLowerCase()){
	//		continue;
	//	  }
			//var TestScenarioid = getAttr(_tests,"id");
		  var tests = testSuite.getElementsByTagName("TestScenario");
		  var t=tests[0]
		  var TestScenarioid = getAttr(t,"id");
		  var ErrorPages = tests[0].getElementsByTagName("ErrorPage");
		  for(var v = 0; v < ErrorPages.length; v++) {
				var ErrorPage= ErrorPages[v];
				var _page = getAttr(ErrorPage,"page");
				Errpagechildcount=ErrorPages[v].childElementCount;

			 //var Errornumber=ErrorPages[v].children[k].childNodes.childElementCount
			  //for(var z = 0; z< ErrorCount; z++) {
			  
               // var Cdate=ErrorPages[v].children[k].childNodes[z].textContent;	


				var tableRow = new TableRow();
				tableRow.className='td';
				tableRow.createLastRow();
				//tableRow1.insertRecord(aTag);
				
				
				
				tableRow.insertRecord(textTag(_suiteName));
				tableRow.insertRecord(textTag(TestScenarioid));
				tableRow.insertRecord(textTag(_page));
				
				
					for(var k = 0; k< Errpagechildcount; k++) {
					var Nodename1=ErrorPages[v].children[k].nodeName;
					var ErrorCount=ErrorPages[v].children[k].childNodes.length;				
					if(Nodename1=="GrammarErrors")
					{
					if(Errpagechildcount>1){
						tableRow.insertRecord(textTag(ErrorCount));
					}else{
						tableRow.insertRecord(textTag(0));
						tableRow.insertRecord(textTag(ErrorCount));
					}

					}
					else if(Nodename1=="SpellErrors"){
					if(Errpagechildcount>1){
						tableRow.insertRecord(textTag(ErrorCount));
					}else{
						tableRow.insertRecord(textTag(ErrorCount));
						tableRow.insertRecord(textTag(0));
					}
					}
					else 
					{
						tableRow.insertRecord(textTag("0"));
					}
				}
			  
			}
		}
	//alert(xml_files_array);
	}
    


function  parseMultimediaerrorSuiteXML_link(appName,test,the_xmlFileName)
{
var control = document.getElementById('lnktable1');
control.style.visibility = "hidden";

	var listDoc = null;
	//var pieces = the_xmlFileName.split(/[\s/]+/);
	//var file1= pieces[pieces.length-1]
	//var splErrFile=the_xmlFileName.replace(file1,"SpellErrors_"+file1);	
	//var linkErrFile=the_xmlFileName.replace(file1,"LinkErrors_"+file1);
	
	var the_doc_spell = CreateXMLObject();
	var the_doc_link = CreateXMLObject();

	the_doc_spell.async=false;
    the_doc_spell.load(the_xmlFileName);
	
	//var the_doc_link=doc;
	//the_doc_link.async=false;
    //the_doc_link.load(linkErrFile);
	

	var rootNode = the_doc_spell.getElementsByTagName("TestSet");	
	
    var childCount=rootNode[0].childElementCount;
	rootNode[0].children;
	for(var i=0; i<rootNode.length; i++ ){		
		 var testSuite = rootNode[i]; 
		 var _suiteName = getAttr(testSuite,"name");
		 var _tests = testSuite.getElementsByTagName("TestScenario");
//		  if(_suiteName.toLowerCase() != appName.toLowerCase()){
	//		continue;
	//	  }
			//var TestScenarioid = getAttr(_tests,"id");
		  var tests = testSuite.getElementsByTagName("TestScenario");
		  var t=tests[0]
		  var TestScenarioid = getAttr(t,"id");
		  var ErrorPages = tests[0].getElementsByTagName("ErrorPage");
		  for(var v = 0; v < ErrorPages.length; v++) {
		  var ErrorPage= ErrorPages[v];
		  var _page = getAttr(ErrorPage,"page");
		  Errpagechildcount=ErrorPages[v].childElementCount;
			for(var k = 0; k< Errpagechildcount; k++) {
		      var Nodename1=ErrorPages[v].children[k].nodeName;
			  var ErrorCount=ErrorPages[v].children[k].childNodes.length;
			 //var Errornumber=ErrorPages[v].children[k].childNodes.childElementCount
			  //for(var z = 0; z< ErrorCount; z++) {
			  
               // var Cdate=ErrorPages[v].children[k].childNodes[z].textContent;	


				var tableRow = new TableRow();
				tableRow.className='td';
				tableRow.createLastRow();
				//tableRow1.insertRecord(aTag);
				
				
				
				tableRow.insertRecord(textTag(_suiteName));
				tableRow.insertRecord(textTag(TestScenarioid));
				tableRow.insertRecord(textTag(_page));
				if(Nodename1=="Exceptions"){
				tableRow.insertRecord(textTag(ErrorCount));
				}
				else{
				tableRow.insertRecord(textTag("0"));
				}
				if(Nodename1=="Error404"){
				tableRow.insertRecord(textTag(ErrorCount));
				}
				else{
				tableRow.insertRecord(textTag("0"));
				}
				if(Nodename1=="Error500"){
				tableRow.insertRecord(textTag(ErrorCount));
				}
				else{
				tableRow.insertRecord(textTag("0"));
				}
				
			  //}
			  
			}
		}

	}
    
}

	var linkErrorerr=new Array();
	function parseMultimediaerrorXMLTOPie(the_xmlFile) 
	{
	 var EroorRoot="../xml/ErrorResults.xml"	
	var pieces = the_xmlFile.split(/[\s/]+/);
	var file1= pieces[pieces.length-1]
	 var linkErrFile=the_xmlFile.replace(file1,"LinkErrors_"+file1);
	linkflag=0;
	var the_doc_Errorlink = CreateXMLObject();
	the_doc_Errorlink.async=false;
	the_doc_Errorlink.load(EroorRoot);	
	
	linkfiles = the_doc_Errorlink.getElementsByTagName("LinkError");		
		for(var n = 0; n < linkfiles.length; n++)
		{
			var linkfile= linkfiles[n];
			var _lnkfile = getAttr(linkfile, "file");			
			if (linkErrFile.search(_lnkfile)>0)
			{
			linkflag=1;
			break;
			}
			
        }
	if(linkflag==1)
	{
	
		
		var listDoc = null;
		listDoc = CreateXMLObject();
		var xmlFile = linkErrFile;		
		var the_doc = listDoc;
		the_doc.async=false;
		the_doc.load(xmlFile);
		var ErrorTypeNodeArr = new Array();
		var rootNode = listDoc.getElementsByTagName("ErrorPage");	
		//var fileNames = rootNode[0].getElementsByTagName("Error404");	
		var childCount=rootNode.length;
		//alert(system_browser_name);		
		for(var i=0; i<childCount; i++ ){
			var Name123=rootNode[i].childElementCount;
			for(var j=0; j<Name123; j++ )
			{		
				var nodename=rootNode[i].children[j].nodeName;

				var ChildCount=rootNode[i].children[j].childNodes.length;
				//var url=rootNode[i].children[j].childNodes[0].data;
				//var url=rootNode[i].children[j].childNodes[1].data;			
				if(!this.contains(ErrorTypeNodeArr,nodename))
				{
					s=[nodename,ChildCount];
					linkErrorerr.push(s);
					ErrorTypeNodeArr.push(nodename);									
					//suiteCountPassArr.push(setName+","+testSenario_pass+","+testSenario_Fail);
					//suiteCountFailArr.push(testSenario_Fail);
				}else{
					var e=this.containsElement(linkErrorerr,nodename);
					var requiredArray=linkErrorerr[e];
					requiredArray[0]=requiredArray[0];
					requiredArray[1]=requiredArray[1]+ChildCount;									
					linkErrorerr[e]=requiredArray;
				}
			}
		}
		drawTestProcessBarChart1234();
		}
	}

	
		var linkErrorerr_spl=new Array();
	function parseMultimediaerrorXMLTOPie_spell(the_xmlFile) 
	{
	 var EroorRoot="../xml/ErrorResults.xml"	
	var pieces = the_xmlFile.split(/[\s/]+/);
	var file1= pieces[pieces.length-1]
	var splErrFile=the_xmlFile.replace(file1,"SpellErrors_"+file1);	
	//var linkErrFile=the_xmlFile.replace(file1,"LinkErrors_"+file1);
	
	spellflag=0;
	var the_doc_Errorlink = CreateXMLObject();
	the_doc_Errorlink.async=false;
	the_doc_Errorlink.load(EroorRoot);
			Spellfiles = the_doc_Errorlink.getElementsByTagName("SpellError");
	
	
		for(var u = 0; u < Spellfiles.length; u++)
		{
			var spellfile= Spellfiles[u];
			var _splfile = getAttr(spellfile, "file");
			if (splErrFile.search(_splfile)>0)
			{
			spellflag=1;
			break;
			}
			
			
        }
		
	if (spellflag==1)
     {		
		var listDoc = null;
		listDoc = CreateXMLObject();
		var xmlFile = splErrFile;		
		var the_doc = listDoc;
		the_doc.async=false;
		the_doc.load(xmlFile);
		var ErrorTypeNodeArr = new Array();
		var rootNode = listDoc.getElementsByTagName("ErrorPage");	
		//var fileNames = rootNode[0].getElementsByTagName("Error404");	
		var childCount=rootNode.length;
		//alert(system_browser_name);		
		for(var i=0; i<childCount; i++ ){
			var Name123=rootNode[i].childElementCount;
			for(var j=0; j<Name123; j++ )
			{		
				var nodename=rootNode[i].children[j].nodeName;

				var ChildCount=rootNode[i].children[j].childNodes.length;
				//var url=rootNode[i].children[j].childNodes[0].data;
				//var url=rootNode[i].children[j].childNodes[1].data;			
				if(!this.contains(ErrorTypeNodeArr,nodename))
				{
					s=[nodename,ChildCount];
					linkErrorerr_spl.push(s);
					ErrorTypeNodeArr.push(nodename);									
					//suiteCountPassArr.push(setName+","+testSenario_pass+","+testSenario_Fail);
					//suiteCountFailArr.push(testSenario_Fail);
				}else{
					var e=this.containsElement(linkErrorerr_spl,nodename);
					var requiredArray=linkErrorerr_spl[e];
					requiredArray[0]=requiredArray[0];
					requiredArray[1]=requiredArray[1]+ChildCount;									
					linkErrorerr_spl[e]=requiredArray;
				}
			}
		}
		drawTestProcessBarChart12345();
	}	
	}

	function drawTestProcessBarChart12345() 
	{        
	
	   
		
		for(var i=0; i < linkErrorerr_spl.length; i++) {
		linkErrorerr_spl[i][0] = linkErrorerr_spl[i][0].replace("GrammarErrors", 'Grammar Errors');
		}	
          	
		
		jQuery.jqplot.config.enablePlugins = true;
		plot7 = jQuery.jqplot('Errorspie_gra',
		[linkErrorerr_spl],
		{
			title: 'Spell & Grammar Errors',
			seriesDefaults: {shadow: true, renderer: jQuery.jqplot.PieRenderer, rendererOptions: { showDataLabels: true } },
			drawBorder: true,
			seriesColors: [ "#E2D415", "#B40431","#0B610B"],
			legend: { show:true}
		}
		);			

	}
	
	
var errordata;
	function drawTestProcessBarChart1234() 
	{  

		for(var i=0; i < linkErrorerr.length; i++) {
		linkErrorerr[i][0] = linkErrorerr[i][0].replace("Error404", '404 Error');
		}	
          	

	
		jQuery.jqplot.config.enablePlugins = true;
		plot7 = jQuery.jqplot('Errorspie_spl',
		[linkErrorerr],
		{
			title: 'Link Errors',
			seriesDefaults: {shadow: true, renderer: jQuery.jqplot.PieRenderer, rendererOptions: { showDataLabels: true } },
			drawBorder: true,
			seriesColors: [ "#E2D415", "#B40431","#0B610B"],
			legend: { show:true}
		}
		);			
		// $('#Errorspie_spl').bind('jqplotDataClick',
		// function (ev, seriesIndex, pointIndex, data) {
		// //alert(pointIndex+"RK"+data);
		// errordata=data;
		// alert(errordata);
		// //document.getElementById('TestProcess_barChart').innerHTML=data;
		// location.href="ErrorList.html";

		// //parseMultimediaerrorXML(errordata)
		// //parseStepLevelXML(Datatext, xmlDoc, appName, browser, baseurl, scenarioId, testCaseId);	
		// // alert("ev = " + ev + " seriesIndex = " + seriesIndex + "pointIndex = " + pointIndex + "data = " + data);
		// }
		// );
	}

function  parseMultimediaerrorHiddentable(the_xmlFileName)
{

	var listDoc = null;
	listDoc = CreateXMLObject();
	var xmlFile =the_xmlFileName;	
	

	

	
	var the_doc = listDoc;
	the_doc.async=false;
	the_doc.load(xmlFile);
	var xml_files_array = new Array();
	var dateFileList=new Array();
	var rootNode = listDoc.getElementsByTagName("TestSet");	
	
    var childCount=rootNode[0].childElementCount;
	//alert(system_browser_name);
	
	rootNode[0].children;
	for(var i=0; i<rootNode.length; i++ ){		
		 var testSuite = rootNode[i]; 
		 var _suiteName = getAttr(testSuite,"name");
		 var tests = testSuite.getElementsByTagName("TestScenario");
		//  if(_suiteName.toLowerCase() != appName.toLowerCase()){
		//	continue;
		 // }
		  var tests = testSuite.getElementsByTagName("TestScenario");
		for(var m = 0; m < tests.length; m++)
		{
		  var t=tests[m]
		  var TestScenarioid = getAttr(t,"id");
		  var ErrorPages = tests[m].getElementsByTagName("ErrorPage");
		  for(var v = 0; v < ErrorPages.length; v++) {
		  var ErrorPage= ErrorPages[v];
		  var _page = getAttr(ErrorPage,"page");
		  Errpagechildcount=ErrorPages[v].childElementCount;
			for(var k = 0; k< Errpagechildcount; k++) {
		      var Nodename1=ErrorPages[v].children[k].nodeName;
			  var ErrorCount=ErrorPages[v].children[k].childNodes.length;
			 //var Errornumber=ErrorPages[v].children[k].childNodes.childElementCount
			  for(var z = 0; z< ErrorCount; z++) {
			  	var errorurl="";
				var ErrorDes="";
                var Cdata=ErrorPages[v].children[k].childNodes[z].textContent;	
				errorurl=Cdata;
				if(xmlFile.search("LinkErrors")==0||Nodename1.search("Exceptions")==0)
				{
				dateFileList=Cdata.split("||");
				var errorurl=dateFileList[0];
				var ErrorDes=dateFileList[1];
				}
 				if(xmlFile.search("LinkErrors")==0||Nodename1.search("Error404")==0||Nodename1.search("404")==0)
				{
				errorurl=Cdata;
				ErrorDes="Page not Found";
				} 
 				if(xmlFile.search("LinkErrors")==0||Nodename1.search("Error500")==0)
				{
				errorurl=Cdata;
				ErrorDes="Internal Server Error";
				}  				
                if (Nodename1.search("GrammarError")==0)
				{
				Nodename1="Grammar";
				}
			   if (Nodename1.search("SpellError")==0)
				{
				Nodename1="Spelling";
				}
				if (Nodename1.search("Error404")==0)
				{
				Nodename1="404";
				}
				
				var tableRow1 = new TableRow1();
				tableRow1.className='td';
				tableRow1.createLastRow();
				//tableRow1.insertRecord(aTag);
				
				tableRow1.insertRecord(textTag(_suiteName));
				tableRow1.insertRecord(textTag(_page));
				tableRow1.insertRecord(textTag(Nodename1));
				tableRow1.insertRecord(textTag(errorurl));
				if(xmlFile.search("SpellErrors")!=0)
				 {
					tableRow1.insertRecord(textTag(ErrorDes));
		         }
				
			  }
			  
			}
			
		}
	}
	//alert(xml_files_array);
}

			for(var i=0; i<rows.length; i++ ){
			if(i % 2 == 0){
				if(rows[i].className.toLowerCase() == "pass")
					rows[i].className = "evenPass";			
				else
					rows[i].className = "evenFail";			
			}else{
				if(rows[i].className.toLowerCase() == "pass")
					rows[i].className = "oddPass";			
				else
					rows[i].className = "oddFail";	
			}
		}
	
	
}

function Details1()
{
    
	 var control = document.getElementById('lnktable1');
		control.style.visibility = "visible";
		parseMultimediaerrorXML(appName,test,the_xmlFileName);
}



function Details()
{
    
	 var control = document.getElementById('lnktable1');
		control.style.visibility = "visible";
		parseMultimediaerrorHiddentable(the_xmlFileName)
}

navigator.sayswho= (function(){
    var N= navigator.appName, ua= navigator.userAgent, tem;
    var M= ua.match(/(opera|chrome|safari|firefox|msie)\/?\s*(\.?\d+(\.\d+)*)/i);
    if(M && (tem= ua.match(/version\/([\.\d]+)/i))!= null) M[2]= tem[1];
    M= M? [M[1], M[2]]: [N, navigator.appVersion, '-?'];

    return M;
})();

var system_browser_name = navigator.sayswho[0];
var system_browser_version = navigator.sayswho[1];

function file_exists (url) {
// Returns true if filename exists
 
var req = this.window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();
if (!req) {throw new Error('XMLHttpRequest not supported');}
// HEAD Results are usually shorter (faster) than GET
req.open('HEAD', url, false);
req.send(null);
if (req.status == 200){
return 1;
}
return 0;
} 


function CheckFile(ss)
{
var fso = new ActiveXObject("Scripting.FileSystemObject");
FileExist = fso.FileExists(ss);
if (FileExist == true){
 alert("True")
 }
 else
{
 alert("FAIL");
 }
}

function checkIfFileExists(path){
    window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, function(fileSystem){
        fileSystem.root.getFile(path, { create: false }, fileExists, fileDoesNotExist);
    }, getFSFail); //of requestFileSystem
}
function fileExists(fileEntry){
    alert("File " + fileEntry.fullPath + " exists!");
}
function fileDoesNotExist(){
    alert("file does not exist");
}
function getFSFail(evt) {
    //console.log(evt.target.error.code);
}