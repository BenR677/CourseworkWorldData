function prepareMap() {

    let map = L.map('map');

    map.createPane('labels');

// This pane is above markers but below popups
    map.getPane('labels').style.zIndex = 650;

// Layers in this pane are non-interactive and do not obscure mouse/touch events
    map.getPane('labels').style.pointerEvents = 'none';

    let cartodbAttribution = '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, &copy; <a href="https://carto.com/attribution">CARTO</a>';

    let positron = L.tileLayer('https://{s}.basemaps.cartocdn.com/light_nolabels/{z}/{x}/{y}.png', {
        attribution: cartodbAttribution
    }).addTo(map);

    let positronLabels = L.tileLayer('http://{s}.basemaps.cartocdn.com/light_only_labels/{z}/{x}/{y}.png', {
        attribution: cartodbAttribution,
        pane: 'labels'
    }).addTo(map);

    geojson = L.geoJson(countries).addTo(map);

    geojson.eachLayer(function (layer) {
        layer.bindPopup(layer.feature.properties.ADMIN);
        /*alert('country = ' + bindPopup(layer.features.properties.ADMIN)); */
    });

    map.setView({lat: 47.040182144806664, lng: 9.667968750000002}, 4);

}