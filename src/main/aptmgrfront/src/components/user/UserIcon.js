import Icon from '../../styles/images/user_icon.png';
import '../../components/Border.css';

const UserIcon = (props) => {
    return (
        <div>
            <img className="thumbnail"
            src={Icon}
            alt=""/>
        </div>
    );
}

export default UserIcon;