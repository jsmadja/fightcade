'use strict';

const Database = require('../database');
const CountryDb = {
    list: () =>
        Database.query(`
            SELECT DISTINCT(country) AS name
            FROM player
            WHERE country != ''
            ORDER BY country ASC
    `)
};
module.exports = CountryDb;
