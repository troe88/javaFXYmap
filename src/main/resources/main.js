var myMap;
var myCollection;
var rectCollection;
var lastPoint;

function testCall(long, lat) {
	java.test(long, lat);
}

function test() {
	ymaps.ready(function() {
		myMap = new ymaps.Map("YMapsID", {
			controls : [],
			center : [ 55.76, 37.64 ],
			zoom : 10
		}), myGeoObject = new ymaps.GeoObject({
			geometry : {
				type : "Point",
				coordinates : [ 55.8, 37.8 ]
			}
		});
		myMap.container.enterFullscreen()
		myMap.events.add('click', function(e) {
			var coords = e.get('coords');
			testCall(coords[1], coords[0])
		});
	});
}

function addPoint(long, lat, ds, isTracking, color) {
	if (myCollection == null) {
		myCollection = new ymaps.GeoObjectCollection();
	}

	if (isTracking == true) {
		myMap.setCenter([ long, lat ], 15, {
			checkZoomRange : true
		});
	}

	lastPoint = new ymaps.Circle([ [ long, lat ], 10 ], {}, {
		fill : true,
		fillColor : color,
		stroke : false
	});
	myCollection.add(lastPoint);
	myMap.geoObjects.add(myCollection);

	addRect(long, lat, ds);
}

function clearMap() {
	myCollection.removeAll();
	rectCollection.removeAll();
}

function centerMap() {
	var long = lastPoint.geometry._7e[0];
	var lat = lastPoint.geometry._7e[1];
	myMap.setCenter([ long, lat ], 15, {
		checkZoomRange : true
	});
}

function addRect(long, lat, d) {
	if (rectCollection == null) {
		rectCollection = new ymaps.GeoObjectCollection();
	} else {
		rectCollection.removeAll();
	}
	var myRectangle = new ymaps.Rectangle([ 
	        [ long - d / 2, lat - d ],
			[ long + d / 2, lat + d ] ], {}, {
		strokeWidth : 2,
		fill : true,
		fillColor : '#00FF0022',
		strokeColor : '#00FF00'
	});
	rectCollection.add(myRectangle);
	myMap.geoObjects.add(rectCollection);
}