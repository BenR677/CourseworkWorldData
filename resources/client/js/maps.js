function polystyle(feature) {
    return {
        //fillColor: 'yellow',
        weight: 2,
        opacity: 1,
        color: 'red',  //Outline color
        fillOpacity: 0
    };
}

function polystyle2(feature) {
    return {
        fillColor: 'blue',
        weight: 20,
        opacity: 1,
        color: 'green',  //Outline color
        fillOpacity: 1
    };
}

function prepareMap() {
    

    let map = L.map('map');

    map.createPane('labels');

// This pane is above markers but below popups
    map.getPane('labels').style.zIndex = 650;

// Layers in this pane are non-interactive and do not obscure mouse/touch events
    map.getPane('labels').style.pointerEvents = 'none';

    let cartodbAttribution = '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, &copy; <a href="https://carto.com/attribution">CARTO</a>';

    let positron = L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/dark_all/{z}/{x}/{y}.png', {
        attribution: cartodbAttribution
    }).addTo(map);
//https://{s}.basemaps.cartocdn.com/light_nolabels/{z}/{x}/{y}.png
    let positronLabels = L.tileLayer('http://{s}.basemaps.cartocdn.com/light_only_labels/{z}/{x}/{y}.png', {
        attribution: cartodbAttribution,
        pane: 'labels'
    }).addTo(map);

    geojson = L.geoJson(countries,{style: polystyle}).addTo(map);

    geojson.eachLayer(function (layer) {
        layer.bindPopup(layer.feature.properties.ADMIN);
        /*alert('country = ' + bindPopup(layer.features.properties.ADMIN)); */

        layer.on('click', function(e) {
            console.log("cClick");
            let countryName = layer.feature.properties.ADMIN;
            geojson = L.geoJson(countries["features"][1][0],{style: polystyle2}).addTo(map);
            runInfoCard(countryName);
            //L.geoJson(polyData).addTo(map);

            //call function to retrieve data on this country
        });

    });

    map.setView({lat: 47.040182144806664, lng: 9.667968750000002}, 4);

}



