import { useState } from "react";
import BigDoor from "./tiles/BigDoor";
import BigDoorInside from "./tiles/BigDoorInside";
import BigDoorInsideTop from "./tiles/BigDoorInsideTop";
import BigDoorLeft from "./tiles/BigDoorLeft";
import BigDoorRight from "./tiles/BigDoorRight";
import BigDoorTop from "./tiles/BigDoorTop";
import CornerBottomLeft from "./tiles/CornerBottomLeft";
import CornerBottomRight from "./tiles/CornerBottomRight";
import CornerTopLeft from "./tiles/CornerTopLeft";
import CornerTopRight from "./tiles/CornerTopRight";
import Door from "./tiles/Door";
import DoorLeft from "./tiles/DoorLeft";
import DoorRight from "./tiles/DoorRight";
import DoorTop from "./tiles/DoorTop";
import Floor from "./tiles/Floor";
import FullWindow from "./tiles/FullWindow";
import FullWindowLeft from "./tiles/FullWindowLeft";
import FullWindowRight from "./tiles/FullWindowRight";
import FullWindowTop from "./tiles/FullWindowTop";
import HalfWindow from "./tiles/HalfWindow";
import HalfWindowLeft from "./tiles/HalfWindowLeft";
import HalfWindowRight from "./tiles/HalfWindowRight";
import HalfWindowTop from "./tiles/HalfWindowTop";
import InsideCornerBottomLeft from "./tiles/InsideCornerBottomLeft";
import InsideCornerBottomRight from "./tiles/InsideCornerBottomRight";
import InsideCornerTopLeft from "./tiles/InsideCornerTopLeft";
import InsideCornerTopRight from "./tiles/InsideCornerTopRight";
import InsideDoor from "./tiles/InsideDoor";
import InsideDoorTop from "./tiles/InsideDoorTop";
import InsidePassage from "./tiles/InsidePassage";
import InsidePassageBig from "./tiles/InsidePassageBig";
import InsidePassageBigTop from "./tiles/InsidePassageBigTop";
import InsidePassageTop from "./tiles/InsidePassageTop";
import InsideWall  from "./tiles/InsideWall";
import InsideWallTop  from "./tiles/InsideWallTop";
import OtherHalfWindow  from "./tiles/OtherHalfWindow";
import OtherHalfWindowLeft  from "./tiles/OtherHalfWindowLeft";
import OtherHalfWindowRight  from "./tiles/OtherHalfWindowRight";
import OtherHalfWindowTop  from "./tiles/OtherHalfWindowTop";
import Passage  from "./tiles/Passage";
import PassageRight  from "./tiles/PassageRight";
import PassageLeft  from "./tiles/PassageLeft";
import PassageTop  from "./tiles/PassageTop";
import Wall  from "./tiles/Wall";
import WallRight  from "./tiles/WallRight";
import WallLeft  from "./tiles/WallLeft";
import WallTop  from "./tiles/WallTop";
import Window  from "./tiles/Window";
import WindowLeft  from "./tiles/WindowLeft";
import WindowRight  from "./tiles/WindowRight";
import WindowTop  from "./tiles/WindowTop";
import Cross  from "./tiles/Cross";
import Cross3  from "./tiles/Cross3";
import Cross3Left from "./tiles/Cross3Left";
import Cross3Right from "./tiles/Cross3Right";
import Cross3Top from "./tiles/Cross3Top";
import Cross3Out from "./tiles/Cross3Out";
import Cross3OutLeft from "./tiles/Cross3OutLeft";
import Cross3OutRight from "./tiles/Cross3OutRight";
import Cross3OutTop from "./tiles/Cross3OutTop";
import Grass from "./tiles/Grass";

const TilesList = (props) => {
    const [doorState, setDoorState] = useState(false);
    const [windowState, setWindowState] = useState(false);
    const [cornerState, setCornerState] = useState(false);
    const [otherState, setOtherState] = useState(false);
    const cornerTiles = [
        {
            name:"CornerBottomLeft",
            side:<CornerBottomLeft/>
        },
        {
            name:"CornerBottomRight",
            side:<CornerBottomRight/>
        },
        {
            name:"CornerTopLeft",
            side:<CornerTopLeft/>
        },
        {
            name:"CornerTopRight",
            side:<CornerTopRight/>
        },
        {
            name:"InsideCornerBottomLeft",
            side:<InsideCornerBottomLeft/>
        },
        {
            name:"InsideCornerBottomRight",
            side:<InsideCornerBottomRight/>
        },
        {
            name:"InsideCornerTopLeft",
            side:<InsideCornerTopLeft/>
        },
        {
            name:"InsideCornerTopRight",
            side:<InsideCornerTopRight/>
        },

    ]
    const windowTiles = [
        {
            name:"FullWindow",
            side:<FullWindow/>
        },
        {
            name:"FullWindowLeft",
            side:<FullWindowLeft/>
        },
        {
            name:"FullWindowRight",
            side:<FullWindowRight/>
        },
        {
            name:"FullWindowTop",
            side:<FullWindowTop/>
        },
        {
            name:"HalfWindow",
            side:<HalfWindow/>
        },
        {
            name:"HalfWindowLeft",
            side:<HalfWindowLeft/>
        },
        {
            name:"HalfWindowRight",
            side:<HalfWindowRight/>
        },
        {
            name:"HalfWindowTop",
            side:<HalfWindowTop/>
        },
        {
            name:"OtherHalfWindow",
            side:<OtherHalfWindow/>
        },
        {
            name:"OtherHalfWindowLeft",
            side:<OtherHalfWindowLeft/>
        },
        {
            name:"OtherHalfWindowRight",
            side:<OtherHalfWindowRight/>
        },
        {
            name:"OtherHalfWindowTop",
            side:<OtherHalfWindowTop/>
        },
        {
            name:"Window",
            side:<Window/>
        },
        {
            name:"WindowLeft",
            side:<WindowLeft/>
        },
        {
            name:"WindowRight",
            side:<WindowRight/>
        },
        {
            name:"WindowTop",
            side:<WindowTop/>
        },
    ]
    const doorTiles = [
        {
            name:"BigDoor",
            side:<BigDoor/>
        },
        {
            name:"BigDoorInside",
            side:<BigDoorInside/>
        },
        {
            name:"BigDoorInsideTop",
            side:<BigDoorInsideTop/>
        },
        {
            name:"BigDoorLeft",
            side:<BigDoorLeft/>
        },
        {
            name:"BigDoorRight",
            side:<BigDoorRight/>
        },
        {
            name:"BigDoorTop",
            side:<BigDoorTop/>
        },
        {
            name:"Door",
            side:<Door/>
        },
        {
            name:"DoorLeft",
            side:<DoorLeft/>
        },
        {
            name:"DoorRight",
            side:<DoorRight/>
        },
        {
            name:"DoorTop",
            side:<DoorTop/>
        },
        {
            name:"InsideDoor",
            side:<InsideDoor/>
        },
        {
            name:"InsideDoorTop",
            side:<InsideDoorTop/>
        },
    ]
    const tiles = [
        {
            name:"Floor",
            side:<Floor/>
        },
        {
            name:"InsidePassage",
            side:<InsidePassage/>
        },
        {
            name:"InsidePassageBig",
            side:<InsidePassageBig/>
        },
        {
            name:"InsidePassageBigTop",
            side:<InsidePassageBigTop/>
        },
        {
            name:"InsidePassageTop",
            side:<InsidePassageTop/>
        },
        {
            name:"InsideWall",
            side:<InsideWall/>
        },
        {
            name:"InsideWallTop",
            side:<InsideWallTop/>
        },
        {
            name:"Passage",
            side:<Passage/>
        },
        {
            name:"PassageRight",
            side:<PassageRight/>
        },
        {
            name:"PassageLeft",
            side:<PassageLeft/>
        },
        {
            name:"PassageTop",
            side:<PassageTop/>
        },
        {
            name:"Wall",
            side:<Wall/>
        },
        {
            name:"WallLeft",
            side:<WallLeft/>
        },
        {
            name:"WallRight",
            side:<WallRight/>
        },
        {
            name:"WallTop",
            side:<WallTop/>
        },

        {
            name:"Cross",
            side:<Cross/>
        },
        {
            name:"Cross3",
            side:<Cross3/>
        },
        {
            name:"Cross3Left",
            side:<Cross3Left/>
        },
        {
            name:"Cross3Right",
            side:<Cross3Right/>
        },
        {
            name:"Cross3Top",
            side:<Cross3Top/>
        },
        {
            name:"Cross3Out",
            side:<Cross3Out/>
        },
        {
            name:"Cross3OutLeft",
            side:<Cross3OutLeft/>
        },
        {
            name:"Cross3OutRight",
            side:<Cross3OutRight/>
        },
        {
            name:"Cross3OutTop",
            side:<Cross3OutTop/>
        },
        {
            name:"Grass",
            side:<Grass/>
        }
    ];
    const [tilesState, SetTilesState] = useState(tiles);
    const [doorTilesState, setDoorTilesState] = useState(doorTiles);
    const updateDoorState = e => {
        setDoorState(!doorState);
    }
    const [windowTilesState, setWindowTilesState] = useState(windowTiles);
    const updateWindowState = e => {
        setWindowState(!windowState);
    }
    const [cornerTilesState, setCornerTilesState] = useState(cornerTiles);
    const updateCornerState = e => {
        setCornerState(!cornerState);
    }
    const updateOtherState = e => {
        setOtherState(!otherState);
    }
    const tileClickHandler = (event, name) => {
        props.setTileCall(name.side);
    };

    return (
            <div>
                <div>
                    <button onClick={updateDoorState}>Doors</button>
                </div>
                <div>
                    { doorState ? doorTilesState.map((tile, i) => {
                        return (
                            <span key={i} name={tile.name} onClick={(e) => tileClickHandler(e, tile)}> {tile.side} </span>);}) : null}
                </div>
                <div>
                    <button onClick={updateWindowState}>Windows</button>
                </div>
                <div>
                    { windowState ? windowTilesState.map((tile, i) => {
                        return (<span key={i} name={tile.name} onClick={(e) => tileClickHandler(e, tile)}> {tile.side} </span>);}) : null}
                </div>
                <div>
                    <button onClick={updateCornerState}>Corners</button>
                </div>
                <div>
                    { cornerState ? cornerTilesState.map((tile, i) => {
                        return (<span key={i} name={tile.name} onClick={(e) => tileClickHandler(e, tile)}> {tile.side} </span>);}) : null}
                </div>
                <div>
                    <button onClick={updateOtherState}>Other</button>
                </div>
                    { otherState ? tilesState.map((tile, i) => {
                        return (<span key={i} name={tile.name} onClick={(e) => tileClickHandler(e, tile)}> {tile.side} </span>);}) : null}
            </div>
    );
}

export default TilesList;