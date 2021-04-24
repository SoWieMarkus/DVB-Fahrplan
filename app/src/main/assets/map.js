var map;
var markersList;
var colors;

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

    markersList = {
        "tram": L.icon({
            iconUrl: "./marker/marker_tram.png",
            iconSize: [16, 16],
            iconAnchor: [8, 8],
            popAnchor: [8, 8],
            shadowUrl: './marker/marker_shadow.png',
            shadowSize: [16, 16],
            shadowAnchor: [7, 7]
        }),
        "bus": L.icon({
            iconUrl: "./marker/marker_bus.png",
            iconSize: [16, 16],
            iconAnchor: [8, 8],
            popAnchor: [8, 8],
            shadowUrl: './marker/marker_shadow.png',
            shadowSize: [16, 16],
            shadowAnchor: [7, 7]
        }),
        "train": L.icon({
            iconUrl: "./marker/marker_train.png",
            iconSize: [16, 16],
            iconAnchor: [8, 8],
            popAnchor: [8, 8],
            shadowUrl: './marker/marker_shadow.png',
            shadowSize: [16, 16],
            shadowAnchor: [7, 7]
        }),
        "lift": L.icon({
            iconUrl: "./marker/marker_lift.png",
            iconSize: [16, 16],
            iconAnchor: [8, 8],
            popAnchor: [8, 8],
            shadowUrl: './marker/marker_shadow.png',
            shadowSize: [16, 16],
            shadowAnchor: [7, 7]
        }),
        "ferry": L.icon({
            iconUrl: "./marker/marker_ferry.png",
            iconSize: [16, 16],
            iconAnchor: [8, 8],
            popAnchor: [8, 8],
            shadowUrl: './marker/marker_shadow.png',
            shadowSize: [16, 16],
            shadowAnchor: [7, 7]
        }),
        "alita": L.icon({
            iconUrl: "./marker/marker_alita.png",
            iconSize: [16, 16],
            iconAnchor: [8, 8],
            popAnchor: [8, 8],
            shadowUrl: './marker/marker_shadow.png',
            shadowSize: [16, 16],
            shadowAnchor: [7, 7]
        }),
        "footpath": L.icon({
            iconUrl: "./marker/marker_footpath.png",
            iconSize: [16, 16],
            iconAnchor: [8, 8],
            popAnchor: [8, 8],
            shadowUrl: './marker/marker_shadow.png',
            shadowSize: [16, 16],
            shadowAnchor: [7, 7]
        }),
        "metropolitan": L.icon({
            iconUrl: "./marker/marker_metropolitan.png",
            iconSize: [16, 16],
            iconAnchor: [8, 8],
            popAnchor: [8, 8],
            shadowUrl: './marker/marker_shadow.png',
            shadowSize: [16, 16],
            shadowAnchor: [7, 7]
        }),
        "unkown": L.icon({
            iconUrl: "./marker/marker_train.png",
            iconSize: [16, 16],
            iconAnchor: [8, 8],
            popAnchor: [8, 8],
            shadowUrl: './marker/marker_shadow.png',
            shadowSize: [16, 16],
            shadowAnchor: [7, 7]
        }),
        "destination": L.icon({
            iconUrl: "./marker/marker_destination.png",
            iconSize: [32, 32],
            iconAnchor: [16, 32],
            popAnchor: [16, 0],
            shadowUrl: './marker/marker_shadow.png',
            shadowSize: [0, 0],
            shadowAnchor: [7, 7]
        })

    }
    colors = {
        "tram": "#dd0b30",
        "bus": "#005d79",
        "train": "#555555",
        "lift": "#009551",
        "ferry": "#00a5dd",
        "alita": "#FFD700",
        "footpath": "#F2F2F2",
        "metropolitan": "#009551",
        "unknown": "#555555"
    }

}

function showRoute(route) {
    let markers = [];

    let routeCopy = route;
    route = [];
    for (let i = 0; i < routeCopy.length;i++) {
        if (routeCopy[i].mode !== "waiting")
            route.push(routeCopy[i]);
    }


    for (let i = 0; i < route.length; i++) {
        let partialRoute = route[i];
        let mode = partialRoute.mode;
        let path = [];
        if (mode === "waiting") continue;
        if (partialRoute.nodes.length < 2) continue;

        for (let j = 0; j < partialRoute.nodes.length; j++) {
            let node = partialRoute.nodes[j];
            let coordinates = [node.x,node.y];
            path.push(coordinates);
            if (j === 0 || j === (partialRoute.nodes.length - 1)) {


                console.log("\nj: "+ j);
                console.log("i: "+ i);
                console.log("imax: "+ route.length);
                console.log("jmax: "+ (partialRoute.nodes.length - 1));
                console.log("mode: "+ mode);
                let marker;
                if (j === (partialRoute.nodes.length -1) && i === (route.length -1)) {
                    console.log("dest")
                    marker = L.marker(coordinates, {icon: markersList["destination"]});
                } else if (j === 0 && i === 0) {
                    console.log("start")
                    marker = L.marker(coordinates);
                } else if (mode !== "footpath") {
                    console.log("normal")
                    marker = L.marker(coordinates, {icon: markersList[mode]});
                } else {
                    console.log("skip")
                    continue;
                }

                marker.bindPopup(node.name);
                marker.addTo(map);
                markers.push(marker);
            }



        }


        L.polyline(path, {
            color: colors[mode],
            weight: 5,
            smoothFactor: 1
            //dashArray: '10, 10', dashOffset: '0'
        }).addTo(map);

    }

    var group = new L.featureGroup(markers);

    map.fitBounds(group.getBounds());
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
        let marker = L.marker(coordinate, {icon: markersList[node.mode]}).bindPopup(node.name);
        marker.addTo(map);
        markers.push(marker);
        path.push(coordinate);

    }
    var group = new L.featureGroup(markers);

    map.fitBounds(group.getBounds());

    L.polyline(path, {
        color: colors[nodes[0].mode],
        weight: 5,
        smoothFactor: 1
    }).addTo(map);
}

function focus(node) {
    map.flyTo([node.x, node.y], 15);
}

function show(node) {
    map.setView([node.x, node.y], 15);
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

