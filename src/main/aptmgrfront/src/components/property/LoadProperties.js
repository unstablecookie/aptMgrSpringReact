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
  

const LoadProperties = props => {
    const chakraTheme = getTheme(DEFAULT_OPTIONS);
    const theme = useTheme(chakraTheme);
    const [getProperties, setGetProperty] = useState({nodes: [],});

    const config = {
      headers: { Authorization: `Bearer ${props.token}` }
    };

    useEffect(() => {
        async function fetchProperties(params) {
            const URL = "/properties";
            try {
                const response = await axios.get(URL, config);
                setGetProperty({
                  nodes: response.data,
                });
            } catch (error) {
                console.log(error);
            }
        }
        fetchProperties();
    }, []);

    if (!getProperties.nodes.length) return <h3>loading..</h3>;

    return (
      <Box p={3} borderWidth="1px" borderRadius="lg">
        <Table data={getProperties} theme={theme}>
        {(tableList) => (
          <>
            <Header>
              <HeaderRow>
                <HeaderCell>id</HeaderCell>
                <HeaderCell>title</HeaderCell>
                <HeaderCell>type</HeaderCell>
              </HeaderRow>
            </Header>
            <Body>
              {tableList.map((property, i) => (
                <Row key={i} item={property}>
                  <Cell>{i}</Cell>
                  <Cell>{property.title}</Cell>
                  <Cell>{property.propertyTypeId}</Cell>
                </Row>
              ))}
            </Body>
          </>
        )}
      </Table>
      </Box>
    );
};

export default LoadProperties;