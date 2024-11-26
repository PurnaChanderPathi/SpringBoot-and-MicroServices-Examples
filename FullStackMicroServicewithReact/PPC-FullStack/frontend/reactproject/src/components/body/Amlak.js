import React from 'react'
import './Amlak.css'
import HomePage from './HomePage'
import Header from '../header/Header'

const Amlak = () => {
  return (
    <div className='mainDiv'>
                <Header/>
      <div className='Heading'>Home Page</div>
      <div className='HomePage'>
            <HomePage />
      </div>
    </div>
  )
}

export default Amlak
