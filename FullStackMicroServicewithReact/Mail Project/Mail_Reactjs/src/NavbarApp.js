import React from 'react'
import Navbar from './ComponentMui/Navbar'
const linkArray = ["Products", "Services", "Overview", "Contact Us"];
const App = () => {
  return (
    <div>
      <Navbar links={linkArray} />
    </div>
  )
}

export default App
