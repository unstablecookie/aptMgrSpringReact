import React from "react";
import DoorImage from './res/door.jpg';
import './Tile.css'

const Door = () => {
    return (
        <img className="myTileImg" src={DoorImage}/>
    )
};

export default Door;