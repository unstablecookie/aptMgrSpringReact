import React, { useEffect, useState }  from 'react';
import axios from "axios";
import { useTheme } from '@table-library/react-table-library/theme';
import { DEFAULT_OPTIONS, getTheme } from '@table-library/react-table-library/chakra-ui';
import { Box } from '@chakra-ui/react';
import {
    Table,
    Header,
    HeaderRow,
    Body,
    Row,
    HeaderCell,
    Cell,
  } from "@table-library/react-table-library/table";
import '../../styles/imageStyles.css'
import '../../styles/util.css'
import HomeIcon from '../../styles/images/home_icon.png';
import PopUpProperty from '../PopUpProperty';

const LoadOwnerProperties = props => {
    const chakraTheme = getTheme(DEFAULT_OPTIONS);
    const theme = useTheme(chakraTheme);
    const [getProperties, setGetProperties] = useState({nodes: [],});
    const [getProperty, setGetProperty] = useState({});
    const config = {
      headers: { Authorization: `Bearer ${props.token}` }
    };
    const [popUpActive, setPopUpActive] = useState(false);

    useEffect(() => {
        async function fetchProperties(params) {
            const URL = "/properties/owner";
            try {
                const response = await axios.get(URL, config);
                setGetProperties({
                  nodes: response.data,
                });
            } catch (error) {
                console.log(error);
            }
        }
        fetchProperties();
    }, []);

    const loadProperty = (e) => {
      async function fetchProperty(params) {
        const URL = `/properties/owner/${params.property.id}`;
        console.log(URL);
        try {
            const response = await axios.get(URL, config);
            setGetProperty(response);
            setPopUpActive(true);
        } catch (error) {
            console.log(error);
        }
      }
      fetchProperty(e);
    }

    const getImagePath = (e) => {
      if(e === null) {
        return {HomeIcon}.HomeIcon;
      } else {
        return `data:image/jpeg;base64,${e}`;
      }
    };

    if (!getProperties.nodes.length) return <h3>loading..</h3>;

    return (
      <div>
        <div className='table-header'>
          <Box p={3} borderWidth="1px" borderRadius="lg">
            <Table data={getProperties} theme={theme}>
            {(tableList) => (
              <>
                <Header>
                  <HeaderRow>
                    <HeaderCell>id</HeaderCell>
                    <HeaderCell>title</HeaderCell>
                    <HeaderCell>type</HeaderCell>
                    <HeaderCell>image</HeaderCell>
                    <HeaderCell>view</HeaderCell>
                  </HeaderRow>
                </Header>
                <Body>
                  {tableList.map((property, i) => (
                    <Row key={i} item={property}>
                      <Cell>{i}</Cell>
                      <Cell>{property.title}</Cell>
                      <Cell>{property.propertyType}</Cell>
                      <Cell>
                        <img className="thumbnail"
                          src={getImagePath(property.data)}
                          alt=""
                        />
                      </Cell>
                      <Cell onClick={() => loadProperty({property})}><div class="plainborder">details</div></Cell>
                    </Row>
                  ))}
                </Body>
              </>
            )}
          </Table>
          </Box>
        </div>
      <div style={{position: 'relative'}}>
        { popUpActive ? <PopUpProperty active={popUpActive} setActive={setPopUpActive} child={getProperty} token={props.token}/> : null }
        </div>
      </div>
    );
};

export default LoadOwnerProperties;