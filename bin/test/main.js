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

		var myPlacemark = new ymaps.Placemark([ 55.8, 37.6 ]);
		var myPolyline = new ymaps.Polyline([ [ 55.80, 37.30 ],
				[ 55.80, 37.40 ], [ 55.70, 37.30 ], [ 55.70, 37.40 ] ]);
		// var myCircle = new ymaps.Circle([[55.76, 37.64], 10000]);

		myCircle.events.add('click', function() {
			alert('qwe');
		});

		// myMap.geoObjects.add(myCircle);
		// myMap.geoObjects.add(myPlacemark);
		// myMap.geoObjects.add(myPolyline);
	});
}
var myCollection;
function addPoint(long, lat) {
	if (myCollection == null) {
		myCollection = new ymaps.GeoObjectCollection();
	} else {
		// myCollection.removeAll();
	}
	// var myCircle = new ymaps.Circle([[long, lat], 1000]);
	myMap.setCenter([ long, lat ], 15, {
		checkZoomRange : true
	});
	myCollection.add(new ymaps.Circle([ [ long, lat ], 10 ]));
	myMap.geoObjects.add(myCollection);
}