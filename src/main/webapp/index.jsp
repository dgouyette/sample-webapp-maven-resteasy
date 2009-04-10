<html>
<head>
    <title>Formulaire2</title>
    <script language="javascript" src="js/dojo.js"></script>
    <script language="javascript">
        dojo.require("dojo.io.*");
        dojo.require("dojo.event.*");


        function getContactXml() {
            var bindArgs = {
                url: "rest/contact/1",
                error: function(type, data, evt) {
                    document.getElementById("output").value = "An error occurred."
                },
                load: function(type, data, evt) {
                    document.getElementById("output").value = data;
                },
                mimetype: "text/plain",
                formNode: document.getElementById("form1")
            };
            dojo.io.bind(bindArgs);


        }
        function reset()
        {
            document.getElementById("output").value = "";
        }

        function getContactJSONs() {
            var bindArgs = {
                url: "rest/contact/1",
                error: function(type, data, evt) {
                    document.getElementById("output").value = "An error occurred."
                },
                load: function(type, data, evt) {
                    document.getElementById("output").value = data;
                },
                mimetype: "application/json",
                formNode: document.getElementById("form1")
            };
            dojo.io.bind(bindArgs);

        }


    </script>

</head>

<body>
<h1>Appeler des services rest avec dojo</h1>
<ul>
    <li><a href="#" onclick="javascript:getContactXml()">Recuperer contact au format xml</a></li>
    <li><a href="#" onclick="javascript:getContactJSONs()">Recuperer contact au format json</a></li>
    <li><a href="#" onclick="javascript:reset()">Reset</a></li>
    <li>---</li>
    <li>output : <br/>

        <form id="form1"> :<textarea rows="10" cols="40" id="output"></textarea></form>
    </li>
</ul>

<hr/>
<i>http://www.cestpasdur.com</i>
</body>
</html>
