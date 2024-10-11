import React, { useEffect, useState } from 'react';
import '../styles/common.css';
import '../styles/darkribbon.css';
import NewPropertyButton from '../components/menu/NewPropertyButton';
import ListPropertiesButton from '../components/menu/ListPropertiesButton';
import LoadOwnerProperties from '../components/property/LoadOwnerProperties';
import NewPropertyByOwner from '../components/property/NewPropertyByOwner';
import TopRibbon from '../components/menu/TopRibbon';

const Dashboard = props => {

    const [showNewProperty, setShowNewProperty] = React.useState(false);
    const onClickNewProperty = () => setShowNewProperty(!showNewProperty);
  
    const [showListProperties, setShowListProperties] = React.useState(false);
    const onClickListProperties = () => setShowListProperties(!showListProperties);

    const toph3 = 'Dashboard';

    return (
        <div>
          <header className="App-header" />
          <TopRibbon topHeader={toph3}/>
          <button className='button-dashboard100'>Properties</button>
          <div onClick={onClickNewProperty}>
            <NewPropertyButton />
          </div>
          <div>
            { showNewProperty ? <NewPropertyByOwner token={props.apptoken} hide={setShowNewProperty}/> : null }
          </div>
          <div onClick={onClickListProperties}>
            <ListPropertiesButton />
          </div>
          <div>
            { showListProperties ? <LoadOwnerProperties token={props.apptoken}/> : null }
          </div>
      </div>
    );
};

export default Dashboard;