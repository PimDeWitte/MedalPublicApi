var http = require("http");
var https = require("https");

/**
 * getJSON:  REST get request returning JSON object(s)
 * @param options: http options object
 * @param callback: callback to pass the results JSON object(s) back
 */
getJSON = function(options, onResult)
{
    console.log("rest::getJSON");

    var prot = options.port == 443 ? https : http;
    var req = prot.request(options, function(res)
    {
        var output = '';
        console.log(options.host + ':' + res.statusCode);
        res.setEncoding('utf8');

        res.on('data', function (chunk) {
            output += chunk;
        });

        res.on('end', function() {
            var obj = JSON.parse(output);
            onResult(res.statusCode, obj);
        });
    });

    req.on('error', function(err) {
        //res.send('error: ' + err.message);
    });

    req.end();
};


var categoriesById = {};
var categoriesByName = {};

function getCategoriesAndRunExamples() {
    var options = {
        host: 'api.gomedal.com',
        port: 443,
        path: '/categories',
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    };
    getJSON(options, function(statusCode, result) {
        result.forEach(function(category) {
            categoriesById[category.categoryId] = category;
            categoriesByName[category.categoryName.toLowerCase()] = category;
        })

        getRandomClip();
        getTopClips(2);
        getTopClipsForSpecificGame(2, "pubg")
    });
}

function getRandomClip() {
    var options = {
        host: 'api.gomedal.com',
        port: 443,
        path: '/botclips?random=1&limit=1',
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    };
    getJSON(options, function(statusCode, result) {
        console.log("Random clip: ####################################################");
        result.forEach(function(randomClip) {
            console.log(randomClip);
        })

    });

}

function getTopClips(amount) {
    var options = {
        host: 'api.gomedal.com',
        port: 443,
        path: '/botclips?limit='+amount,
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    };

    getJSON(options, function(statusCode, result) {
        console.log(amount + " top clips: ####################################################");
        console.log(result);

    });

}

function getTopClipsForSpecificGame(amount, game) {
    var options = {
        host: 'api.gomedal.com',
        port: 443,
        path: '/botclips?limit='+amount+"&categoryId="+categoriesByName[game.toLowerCase()].categoryId,
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    };

    getJSON(options, function(statusCode, result) {
        console.log(amount + " top clips for " + game + " ####################################################");
        console.log(result);

    });

}



getCategoriesAndRunExamples();