import React from "react";
import FloorImage from './res/floor.jpg';
import './Tile.css'

const Floor = () => {
    return (
        <img className="myTileImg" src={FloorImage}/>
    )
};

export default Floor;