import React from "react";
import GrassImage from './res/grass.jpg';
import './Tile.css'

const Grass = () => {
    return (
        <img className="myTileImg" src={GrassImage}/>
    )
};

export default Grass;