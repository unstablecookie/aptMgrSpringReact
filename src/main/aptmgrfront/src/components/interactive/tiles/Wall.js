import React from "react";
import WallImage from './res/wall.jpg';
import './Tile.css'

const Wall = () => {
    return (
        <img className="myTileImg" src={WallImage}/>
    )
};

export default Wall;