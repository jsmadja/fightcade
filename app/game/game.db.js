'use strict';

const Database = require('../database');
const GameDb = {
    list: () =>
        Database.query(`
            SELECT rom,name
            FROM stat
            ORDER BY name ASC
    `)
};
module.exports = GameDb;
