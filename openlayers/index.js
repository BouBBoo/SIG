import 'ol/ol.css';
import {Map, View} from 'ol';
import Tile from 'ol/layer/Tile';
import TileWMS from 'ol/source/TileWMS';

var map = new Map({
    target: 'map',
    layers: [
      new Tile({
        title: 'Global Imagery',
        source: new TileWMS({
          url: 'http://172.17.0.1:8080/geoserver/wms',
          crossOrigin: 'anonymous' ,
          params: {LAYERS: 'SIG:SalleE0,SIG:EscalierE0', TILED: true}
        })
      })
    ],
    view: new View({
      projection: 'EPSG:4326',
      center: [0, 0],
      zoom: 5,
      maxResolution: 0.703125
    })
  });
