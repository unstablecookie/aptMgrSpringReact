import '../../styles/darkribbon.css'
import '../PopUp.css'
import DeleteButton from './DeleteButton'
import DeleteService from '../DeleteService';
import UpdateButton from './UpdateButton';
import PatchServie from '../PatchServie';

const RightRibbon = ({child, token, popUpActive, entityFull, entity}) => {
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

    return (
        <div className="example-wrapper-right">
            <div onClick={onClickDelete}>
                <DeleteButton/>
            </div>
            <div onClick={onClickUpdate}>
                <UpdateButton/>
            </div>
        </div>
    );
};

export default RightRibbon;