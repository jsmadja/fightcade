'use strict';

const Database = require('../database');
const PlayerDb = {
    getAttendance: (game, country) => {
        let query = 'SELECT date, COUNT(DISTINCT player) AS count FROM player ';
        if (country) {
            query += `WHERE country = '${country}' `;
        }
        if (game) {
            if (country) {
                query += `AND rom = '${game}'`;
            } else {
                query += `WHERE rom = '${game}'`;
            }
        }
        query += ' GROUP BY DAY(date), HOUR(date) ORDER BY date ASC';
        return Database.query(query);
    }
};
module.exports = PlayerDb;
