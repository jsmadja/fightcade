'use strict';

const express = require('express');

const allowCrossDomain = (req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,PATCH');
    res.header('Access-Control-Allow-Headers', 'Content-Type');
    next();
};


const app = express();
const port = 8765;

const CountryService = require('./country/country.service');

app.use(allowCrossDomain);

app.get('/countries', (req, res) =>
    CountryService
        .find()
        .then(results => res.status(200).json(results)));
// endregion

app.listen(port, () =>
    console.log(`Fightcade running on port ${port}`));

module.exports = app;