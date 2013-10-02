var win = Ti.UI.createWindow({
    backgroundColor : "#000"
});

var g = require("miga.tigraph");

var points = [[0, 0], [10, 5], [20, 25], [30, 5]];
var blob = g.drawLines({
    width : 400, height : 200, maxY : 50, circles : true, circleRadius : 5, circleColor : "#ff0000", backgroundColor : "#ffffff", color : "#000000", lineWidth : 2, points : points
});

var img = Ti.UI.createImageView({
    image : blob, top : 0
});
win.add(img);

win.open();
