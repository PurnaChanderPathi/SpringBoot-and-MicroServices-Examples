import React from 'react'
import './MashreqBody.css'
import WidgetsIcon from '@mui/icons-material/Widgets';
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank';
import PowerSettingsNewIcon from '@mui/icons-material/PowerSettingsNew';
import DoubleArrowIcon from '@mui/icons-material/DoubleArrow';
import { Typography } from '@mui/material';

const MashreqBody = () => {
    return (
        <div className='MashMD'>
            <div className='tablogout'>
                <div className='topDiv'>
                    <WidgetsIcon sx={{ color: '#FF5E00' }} />
                    <CheckBoxOutlineBlankIcon sx={{ color: '#FF5E00' }} />
                    <PowerSettingsNewIcon sx={{ color: '#FF5E00' }} />
                </div>
                <div className='bottomDiv'>
                    <DoubleArrowIcon sx={{ color: '#FF5E00' }} />
                </div>
            </div>

            <div className='MasqBodyMD'>
                <div className='CaseDetailsMD'>
                    <div className='CaseDetails'>
                        <div className='CaseDetailsHeading'>
                            <Typography sx={{fontWeight: 'bold'}}>
                               <span style={{ textDecoration: 'underline',
                                textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                               textUnderlineOffset: '4px'  }} 
                               className='underlineText'>Cas</span>e Details
                            </Typography>
                        </div>
                        <div className='CaseDetailsDIv'>
                        <div className='ReviewIdCD'>
                            <div className='ReviewLabelCD'>
                                ReviewId
                            </div>
                            <div className='ReviewInputCD'>
                                <input type='text'
                                    className='inputReviewCD'
                                    disabled />
                            </div>
                        </div>
                        <div className='ReviewIdCD'>
                            <div className='ReviewLabelCD'>
                                Division
                            </div>
                            <div className='ReviewInputCD'>
                                <input type='text'
                                    className='inputReviewCD'
                                    disabled />
                            </div>
                        </div>
                        <div className='ReviewIdCD'>
                            <div className='ReviewLabelCD'>
                                Group Name
                            </div>
                            <div className='ReviewInputCD'>
                                <input type='text'
                                    className='inputReviewCD'
                                    disabled />
                            </div>
                        </div>
                        <div className='ReviewIdCD'>
                            <div className='ReviewLabelCD'>
                                Role
                            </div>
                            <div className='ReviewInputCD'>
                                <input type='text'
                                    className='inputReviewCD'
                                    disabled />
                            </div>
                        </div>
                        <div className='ReviewIdCD'>
                            <div className='ReviewLabelCD'>
                                FCR Initator
                            </div>
                            <div className='ReviewInputCD'>
                                <input type='text'
                                    className='inputReviewCD'
                                    disabled />
                            </div>
                        </div>
                        <div className='ReviewIdCD'>
                            <div className='ReviewLabelCD'>
                                Case Status
                            </div>
                            <div className='ReviewInputCD'>
                                <input type='text'
                                    className='inputReviewCD'
                                    disabled />
                            </div>
                        </div>
                        </div>
                        
                    </div>
                    <div className='CaseTimeline'>

                    </div>
                </div>
            </div>

        </div>
    )
}

export default MashreqBody
