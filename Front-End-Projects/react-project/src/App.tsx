import React from 'react'
import './App.css'
import Timer from './components/Timer'
const App: React.FC = () => {
  return (
    <div className="container typography"><Timer city="London"/>
    <Timer city="Delhi"></Timer>
    <Timer city="Paris"></Timer>
    <Timer city="Moscow"></Timer>
    <Timer city="Jerusalem"></Timer>
    <Timer city="Yekaterinburg "></Timer>
    <Timer city="Kyiv"></Timer>
    <Timer city="Beirut"></Timer>
    <Timer city="Krasnoyarsk"></Timer>
    <Timer city="Vladivostok"></Timer>
    <Timer city="Los_Angeles"></Timer>
    <Timer city="Mexico"></Timer>
    <Timer city="Buenos_Aires"></Timer>
    <Timer city="Toronto"></Timer>
    <Timer city="Sydney"></Timer>
    <Timer city="Kamchatka"></Timer>
   </div>
  )
}

export default App