import 'ol/ol.css';
import {Map, View} from 'ol';
import Tile from 'ol/layer/Tile';
import TileWMS from 'ol/source/TileWMS';

const url = 'http://192.168.1.21:8080/geoserver/wms';

const query = window.location.href;

console.log(query);

var map;
if(query.includes('etage0')){
  map = new Map({
    target: 'map',
    layers: [
      new Tile({
        title: 'Global Imagery',
        source: new TileWMS({
          url: url,
          crossOrigin: 'anonymous' ,
          params: {LAYERS: 'SIG:SalleE0,SIG:EscalierE0,SIG:position', TILED: true}
        })
      })
    ],
    view: new View({
      projection: 'EPSG:4326',
      center: [0, 0],
      zoom: 6,
      maxResolution: 0.703125
    })
  });
}else if(query.includes('etage1')){
  map = new Map({
    target: 'map',
    layers: [
      new Tile({
        title: 'Global Imagery',
        source: new TileWMS({
          url: url,
          crossOrigin: 'anonymous' ,
          params: {LAYERS: 'SIG:SalleE1,SIG:EscalierE1,SIG:position', TILED: true}
        })
      })
    ],
    view: new View({
      projection: 'EPSG:4326',
      center: [0, 0],
      zoom: 6,
      maxResolution: 0.703125
    })
  });
}else{
  map = new Map({
    target: 'map',
    layers: [
      new Tile({
        title: 'Global Imagery',
        source: new TileWMS({
          url: url,
          crossOrigin: 'anonymous' ,
          params: {LAYERS: 'SIG:SalleE0,SIG:EscalierE0,SIG:SalleE1,SIG:EscalierE1,SIG:position', TILED: true}
        })
      })
    ],
    view: new View({
      projection: 'EPSG:4326',
      center: [0, 0],
      zoom: 6,
      maxResolution: 0.703125
    })
  });
}


