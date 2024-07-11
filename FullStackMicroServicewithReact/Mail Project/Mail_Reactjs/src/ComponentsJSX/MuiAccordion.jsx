import React, { useState } from 'react';
import { Accordion, AccordionSummary, AccordionDetails, Typography } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

function MuiAccordion() {
  const [expanded, setExpanded] = useState(false);

  const handleChange = (isExpanded, panel) => {
    setExpanded(isExpanded ? panel : false);
  };

  return (
    <div>
      <Accordion
        expanded={expanded === 'panel1'}
        onChange={(event, isExpanded) => {
          handleChange(isExpanded, 'panel1');
        }}
      >
        <AccordionSummary id="panel1-header" aria-controls="panel1-content" expandIcon={<ExpandMoreIcon />}>
          <Typography>Accordion 1</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography>
            Browse through the icons below to find the one you need.
            The search field supports synonyms—for example, try searching for "hamburger" or "logout."
          </Typography>
        </AccordionDetails>
      </Accordion>

      <Accordion
        expanded={expanded === 'panel2'}
        onChange={(event, isExpanded) => {
          handleChange(isExpanded, 'panel2');
        }}
      >
        <AccordionSummary id="panel2-header" aria-controls="panel2-content" expandIcon={<ExpandMoreIcon />}>
          <Typography>Accordion 2</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography>
            Browse through the icons below to find the one you need.
            The search field supports synonyms—for example, try searching for "hamburger" or "logout."
          </Typography>
        </AccordionDetails>
      </Accordion>

      <Accordion
        expanded={expanded === 'panel3'}
        onChange={(event, isExpanded) => {
          handleChange(isExpanded, 'panel3');
        }}
      >
        <AccordionSummary id="panel3-header" aria-controls="panel3-content" expandIcon={<ExpandMoreIcon />}>
          <Typography>Accordion 3</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography>
            Browse through the icons below to find the one you need.
            The search field supports synonyms—for example, try searching for "hamburger" or "logout."
          </Typography>
        </AccordionDetails>
      </Accordion>
    </div>
  );
}

export default MuiAccordion;
