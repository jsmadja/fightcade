'use strict';

var Promise = this.Promise || require('promise');
var request = require('superagent-promise')(require('superagent'), Promise);
const host = 'localhost:' + (process.env.PORT || 8765);

const execute = (fn) =>
    fn
        .end((err) => {
            if (err) Promise.reject(err);
        });

module.exports = {
    getCountries: () => execute(request.get(`${host}/countries`)),
    getGames: () => execute(request.get(`${host}/games`)),
    getAttendance: (game, country, offset) => execute(request.get(`${host}/line?country=${country}&game=${game}&offset=${offset}&forced_offset=-60`))
};