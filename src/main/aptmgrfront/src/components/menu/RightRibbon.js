import React, { useEffect, useState }  from 'react';
import '../../styles/darkribbon.css'
import '../PopUp.css'
import DeleteButton from './DeleteButton'
import DeleteService from '../DeleteService';
import UpdateButton from './UpdateButton';
import PlanButton from './PlanButton';
import PatchServie from '../PatchServie';

const RightRibbon = ({child, token, popUpActive, isPlanActive, entityFull, entity}) => {
    const config = {
        headers: { Authorization: `Bearer ${token}` }
    };

    const onClickDelete = (e) => {
        e.preventDefault();
        const URL = `/${entity}/${child.data.id}`;
        DeleteService.deleteEntity(URL, config)
            .catch((error) => {
            console.log(error);
        });
        popUpActive(false);
    }

    const onClickUpdate = (e) => {
        e.preventDefault();
        const URL = `/${entity}/${child.data.id}`;
        PatchServie.updateEntity(URL, entityFull, config)
            .catch((error) => {
            console.log(error);
        });
    }

    const onClickActive = (e) => {
        isPlanActive(true);
        popUpActive(false);
    }

    return (
        <div className="example-wrapper-right">
            <div onClick={onClickDelete}>
                <DeleteButton/>
            </div>
            <div onClick={onClickUpdate}>
                <UpdateButton/>
            </div>
            { entity == "properties" ?             
                <div onClick={onClickActive}>
                    <PlanButton/>
                </div> : null 
            }
        </div>
    );
};

export default RightRibbon;