import React from "react";
import { useState } from "react";
import PopUpTest from './PopUpTest';
import TilesList from './TilesList.js';
import NeutralTile from './tiles/NeutralTile.js';

const DynamicTile = (props) => {
    const [popUpActive, setPopUpActive] = useState(false);

    const [CurrentTile, setDynamicTile] = useState(<NeutralTile/>);

    const tilesChanged = (props) => {
        changeTile(props);
      };

    const changeTile = (props) => {
        setDynamicTile(props);
        setPopUpActive(false);
    };
    

    return (
        <>
            <div onClick={() => setPopUpActive(true)}>
                {CurrentTile}
            </div>
            <PopUpTest active={popUpActive} setActive={setPopUpActive} >
                <TilesList setTileCall={tilesChanged}/>
            </PopUpTest>
        </>

        
    );
}

export default DynamicTile;