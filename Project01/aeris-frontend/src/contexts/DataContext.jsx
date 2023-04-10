import React, { createContext, useState } from 'react';

export const DataContext = createContext();


export default function DataContextProvider(props) {
  const [searchQuery, setSearchQuery] = useState();

  const newSearchQuery = (novoEstado) => {
    setSearchQuery(novoEstado);
  };

  return (
    <DataContext.Provider value={{ searchQuery, newSearchQuery }}>
      {props.children}
    </DataContext.Provider>
  );
}
