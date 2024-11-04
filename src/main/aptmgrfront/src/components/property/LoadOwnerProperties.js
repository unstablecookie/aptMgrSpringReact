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
import { usePagination } from "@table-library/react-table-library/pagination";
import '../../styles/imageStyles.css';
import '../../styles/util.css';
import '../menu/button-next-page.css';
import HomeIcon from '../../styles/images/home_icon.png';
import PopUpProperty from '../PopUpProperty';

const LoadOwnerProperties = props => {
    const LIMIT = 5;
    const chakraTheme = getTheme(DEFAULT_OPTIONS);
    const theme = useTheme(chakraTheme);
    const [getProperties, setGetProperties] = useState({nodes: [],});
    const [getProperty, setGetProperty] = useState({});
    const config = {
      headers: { Authorization: `Bearer ${props.token}` },
      params: {
        from: 0,
        size: LIMIT,
      },
    };
    const [popUpActive, setPopUpActive] = useState(false);
    const [counter, setCounter] = useState(0);

    const pagination = usePagination(
      getProperties,
      {
        state: {
          page: 0,
          size: LIMIT,
        },
        onChange: onPaginationChange,
      },
      {
        isServer: true,
      }
    );
    const [search, setSearch] = useState("");

    const handleSearch = (e) => {
      setSearch(e.target.value);
      async function fetchPagedPropertiesSearch(params) {
        const URL = "/properties/search/owner";
        const updateConfig = {
          headers: { Authorization: `Bearer ${props.token}` },
          params: {
            title: params,
            from: 0,
            size: LIMIT,
          },
        };
        try {
            const response = await axios.get(URL, updateConfig);
            setGetProperties({
              nodes: response.data,
            });
        } catch (error) {
            console.log(error);
        }
      }
      fetchPagedPropertiesSearch(e.target.value);
    };

    function onPaginationChange(action, state) {
      async function fetchPagedProperties(params) {
        const URL = "/properties/owner";
        const updateConfig = {
          headers: { Authorization: `Bearer ${props.token}` },
          params: {
            from: (params * LIMIT),
            size: LIMIT,
          },
        };
        try {
            const response = await axios.get(URL, updateConfig);
            setGetProperties({
              nodes: response.data,
            });
        } catch (error) {
            console.log(error);
        }
      }
      async function fetchPagedPropertiesSearch(params) {
        const URL = "/properties/search/owner";
        const updateConfig = {
          headers: { Authorization: `Bearer ${props.token}` },
          params: {
            title: search,
            from: (params * LIMIT),
            size: LIMIT,
          },
        };
        try {
            const response = await axios.get(URL, updateConfig);
            setGetProperties({
              nodes: response.data,
            });
        } catch (error) {
            console.log(error);
        }
      }
      if (search == "") {
        fetchPagedProperties(action.payload.page);
      } else {
        fetchPagedPropertiesSearch(action.payload.page);
      }
    }

    useEffect(() => {
      async function fetchCount(params) {
        const URL = "/properties/owner/count";
        try {
          const response = await axios.get(URL, config);
          var maxEntities;
          if (response.data <= LIMIT) {
            maxEntities = 1;
          } else {
            maxEntities = Number((response.data / LIMIT).toFixed())  + 1;
          }
          setCounter(maxEntities);
          } catch (error) {
          console.log(error);
        }
      }
      fetchCount();
    }, []);

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

    return (
      <div>
        <div className='table-header'>
            <label >
                    Search by Title:
                    <input className='simple-input' id="search" type="text" value={search} onChange={handleSearch}/>
            </label>
          <Box p={3} borderWidth="1px" borderRadius="lg">
            <Table data={getProperties} pagination={pagination} 
            theme={theme}
            layout={{ isDiv: true, fixedHeader: true }}>
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
                      <Cell>{property.id}</Cell>
                      <Cell>{property.title}</Cell>
                      <Cell>{property.propertyType}</Cell>
                      <Cell>
                        <img className="thumbnail"
                          src={getImagePath(property.data)}
                          alt=""
                        />
                      </Cell>
                      <Cell onClick={() => loadProperty({property})}><div className="plainborder">details</div></Cell>
                    </Row>
                  ))}
                </Body>
              </>
            )}
          </Table>
            {true && (
              <div
                style={{
                  display: "flex",
                  justifyContent: "space-between",
                }}
              >
                <span>Total Pages: {counter}</span>

                <span>
                  Page:{" "}
                  {Array(counter)
                    .fill()
                    .map((_, index) => (
                      <button className='modern-nextpage'
                        key={index}
                        type="button"
                        onClick={() => pagination.fns.onSetPage(index)}
                      >
                        {index + 1}
                      </button>
                    ))}
                </span>
              </div>
            )}
          </Box>
        </div>
      <div style={{position: 'relative'}}>
        { popUpActive ? <PopUpProperty active={popUpActive} setActive={setPopUpActive} child={getProperty} token={props.token}/> : null }
        </div>
      </div>
    );
};

export default LoadOwnerProperties;