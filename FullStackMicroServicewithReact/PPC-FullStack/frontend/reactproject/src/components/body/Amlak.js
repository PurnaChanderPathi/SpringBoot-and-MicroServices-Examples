import React from 'react'
import './Amlak.css'
import HomePage from './HomePage'
import Header from '../header/Header'
import MashreqHeader from '../header/MashreqHeader'

const Amlak = () => {
  return (
    <div className='mainDiv'>
                <MashreqHeader  />
      {/* <div className='Heading'><span style={{ textDecoration: 'underline',
                                textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                               textUnderlineOffset: '4px'  }} >Hom</span>e Page</div> */}
      <div className='HomePage'>
            <HomePage />
      </div>
    </div>
  )
}

export default Amlak
