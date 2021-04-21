
var map;

function initializeMap() {
    let mapOptions = {
        center: [50.95, 13.45], // Zentrum der Karte
        zoom: 8, //Zoomlevel
        layers: [] // Layer der Map, hier erstmal leer, wird gleich erweitert.
    };

    let divIdOfMap = "mapid";
    map = L.map(divIdOfMap, mapOptions);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        'attribution': 'Kartendaten &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> Mitwirkende',
        'useCache': true
    }).addTo(map);
}

function showTrip(nodes) {

    let path = [];
    let markers = [];

    for (let i = 0; i < nodes.length; i++) {
        let node = nodes[i];
        let coordinate = [node.x, node.y];

        if (node.position === "Current") {
            map.setView(coordinate, 15);
        }
        let marker = L.marker(coordinate).bindPopup(node.name);
        marker.addTo(map);
        markers.push(marker);
        path.push(coordinate);

    }
    var group = new L.featureGroup(markers);

    map.fitBounds(group.getBounds());

    L.polyline(path).addTo(map);


}

function focus(node) {
    map.flyTo([node.x, node.y], 15);
}

/*var url_string = window.location.href;
   var url = new URL(url_string);
   var x = url.searchParams.get("x").split(",");

   var pathCoords =[];
   for (let i = 0; i< x.length; i+=2){
       L.marker([x[i],x[i+1]]).addTo(map);
       pathCoords.push([x[i],x[i+1]]);
   }

   var pathLine = L.polyline(pathCoords).addTo(map)*/

