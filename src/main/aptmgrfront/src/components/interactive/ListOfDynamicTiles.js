import React from "react";
import html2canvas from 'html2canvas';
import { useRef } from 'react';
import './ListPopUp.css';
import './ListOfDynamicTiles.css'
import ArrayOfTiles from "./ArrayOfTiles";


const ListOfDynamicTiles = ({active, isPlanActive}) => {
    const ArraysOfArrays = [
        {
            id: 0,
            data: ArrayOfTiles
        },
        {
            id: 1,
            data: ArrayOfTiles
        },
        {
            id: 2,
            data: ArrayOfTiles
        },
        {
            id: 3,
            data: ArrayOfTiles
        },
        {
            id: 4,
            data: ArrayOfTiles
        },
        {
            id: 5,
            data: ArrayOfTiles
        },
        {
            id: 6,
            data: ArrayOfTiles
        },
        {
            id: 7,
            data: ArrayOfTiles
        },
        {
            id: 8,
            data: ArrayOfTiles
        },
        {
            id: 9,
            data: ArrayOfTiles
        },
        {
            id: 10,
            data: ArrayOfTiles
        },
        {
            id: 11,
            data: ArrayOfTiles
        },
        {
            id: 12,
            data: ArrayOfTiles
        },
    ];
    const myRef = useRef();

    const exportPlan = async (e) => {
        const element = myRef.current;
        const canvas = await html2canvas(element);

        const data = canvas.toDataURL('image/jpg');
        const link = document.createElement('a');

        if (typeof link.download === 'string') {
            link.href = data;
            link.download = 'image.jpg';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
          } else {
            window.open(data);
        }
    };

    return (
        <>
          <div className={active ? "list-popupc active" : "list-popupc"} onClick={ () => isPlanActive(false)}>
                <div ref={myRef} className={active ? "list-popupc_content active" : "list-popupc_content"} onClick={e => e.stopPropagation()}>
                    <div className="tiles_table">
                        {ArraysOfArrays.map((array)=> {
                            return (
                            <div key={array.id} className="tiles">
                                {array.data.map((item) => {
                                    return (
                                        <div key={item.id}>
                                            {item.element}
                                        </div>
                                    );
                                })}
                            </div>);
                        })}
                    </div>
                </div>
                <button className="submit_button submit_button-link" onClick={exportPlan}>SAVE</button>
            </div>
        </>
    );
}

export default ListOfDynamicTiles;