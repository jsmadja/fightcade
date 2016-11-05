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
const GameService = require('./game/game.service');
const PlayerService = require('./player/player.service');

app.use(allowCrossDomain);

app.get('/countries', (req, res) =>
    CountryService
        .find()
        .then(countries => res.jsonp(countries)));

app.get('/games', (req, res) =>
    GameService
        .find()
        .then(games => res.jsonp(games)));

app.get('/line', (req, res) =>
    PlayerService
        .getAttendance(req.query.game, req.query.country, req.query.offset, req.query.forced_offset)
        .then(attendance => res.json(attendance))
);

//noinspection Eslint
app.listen(port, () =>
    console.log(`Fightcade running on port ${port}`));

module.exports = app;