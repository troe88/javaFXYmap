var myMap;

function test() {
	ymaps.ready(function() {
		myMap = new ymaps.Map("YMapsID", {
			controls : [ "zoomControl", "fullscreenControl" ],
			center : [ 55.76, 37.64 ],
			zoom : 10
		}), myGeoObject = new ymaps.GeoObject({
			geometry : {
				type : "Point",
				coordinates : [ 55.8, 37.8 ]
			}
		});

//		var myPlacemark = new ymaps.Placemark([ 55.8, 37.6 ]);
		myPolyline = new ymaps.Polyline([ [ 55.80, 37.30 ],
				[ 55.80, 37.40 ], [ 55.70, 37.30 ], [ 55.70, 37.40 ] ]);
		// var myCircle = new ymaps.Circle([[55.76, 37.64], 10000]);

		// myCircle.events.add('click', function() {
		// alert('qwe');
		// });

		// myMap.geoObjects.add(myCircle);
		// myMap.geoObjects.add(myPlacemark);
		// myMap.geoObjects.add(myPolyline);
	});
}

var myCollection;
var lastPoint;
function addPoint(long, lat, isTracking) {
	if (myCollection == null) {
		myCollection = new ymaps.GeoObjectCollection();
	} else {
		// myCollection.removeAll();
	}

	if (isTracking == true) {
		myMap.setCenter([ long, lat ], 15, {
			checkZoomRange : true
		});
	}
	lastPoint = new ymaps.Circle([ [ long, lat ], 10 ]);
	myCollection.add(lastPoint);
	myMap.geoObjects.add(myCollection);
}

function clearMap() {
	myCollection.removeAll();
}

function centerMap() {
	var long = lastPoint.geometry._7e[0];
	var lat = lastPoint.geometry._7e[1];
	myMap.setCenter([ long, lat ], 15, {
		checkZoomRange : true
	});
}