'use strict';

const Mysql = require('mysql');
const Promise = require('bluebird');
const log = require('winston');

const connection = Mysql.createConnection({
    host: 'hiscores.shmup.com', //process.env.RDS_HOST,
    user: 'fightcade',//process.env.RDS_USER,
    port: 3306, //process.env.RDS_PORT,
    password: 'fightcade', //process.env.RDS_PASSWORD,
    database: 'fightcade_test', //process.env.RDS_DATABASE,
    debug: false
});
connection.connect();

const query = (sql, values) => {
    if (connection.debug) {
        //noinspection Eslint
        console.log(sql);
    }
    values = values || [];
    return new Promise((resolve, reject) => {
        connection.query(sql, (err, rows) => {
            if (err) {
                log.error(err.message);
                reject(err);
            } else {
                resolve(rows);
            }
        }, values);
    });
};

const Database = {
    query
};

module.exports = Database;