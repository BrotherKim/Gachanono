const gacha = {
    init: function () {
        const self = this;
        self.initUI();
    },
    initUI: function () {
        const self = this;
        const mainTableName = '#layoutSidenav_content';
        const mainTable = new Vue({
            el: mainTableName,
            data: function () {
                return {
                    game: {
                        selected: ''
                    },
                    item: {
                        list: [
                            {
                                id: '-1',
                                itemname: '게임을 먼저 선택해주세요'
                            }
                        ],
                        selected: ''
                    },
                    gacha: {
                        selected: ''
                    },
                    message: 'Hello Vue!'
                };
            },
            mounted: function () {},
            methods: {
                GameSelected: function () {
                    var self = this;
                    $
                        .ajax({
                            type: 'GET',
                            url: '/api/prob/itemlist/' + self.game.selected,
                            dataType: 'JSON',
                            contentType: 'application/json; charset=utf-8',
                            success: function (retval) {
                                self.item.list = retval.content;
                            }
                        })
                        .done(function () {
                            self.CreateCrawledProbTable(self.game.selected);
                        })
                        .fail(function (error) {
                            alert(JSON.stringify(error));
                        });
                },
                GachaSelected: function () {
                    var self = this;
                    self.CreateUserProbTable(self.gacha.selected);
                },
                CreateCrawledProbTable: function (gameid) {
                    const crawledTableName = '#crawledProbTable';
                    $(crawledTableName).empty();
                    var crawledProbTable = new Vue({
                        el: crawledTableName,
                        mounted: function () {
                            let spreadsheet = jspreadsheet(this.$el, {
                                minDimensions: [
                                    30, 30
                                ],
                                defaultColWidth: 100,
                                tableOverflow: true,
                                tableWidth: "100%",
                                csv: '/assets/crawled/' + gameid + '.csv',
                                csvHeaders: false,
                                editable: false
                            });
                            Object.assign(this, spreadsheet);
                        }
                    });
                },
                CreateUserProbTable: function (gachaid) {
                    const userTableName = '#userProbTable';
                    $(userTableName).empty();
                    var userProbTable = new Vue({
                        el: userTableName,
                        mounted: function () {
                            let spreadsheet = jspreadsheet(this.$el, {
                                minDimensions: [
                                    30, 30
                                ],
                                defaultColWidth: 100,
                                tableOverflow: true,
                                tableWidth: "100%",
                                csv: '/assets/selection/' + gachaid + '.csv',
                                csvHeaders: false,
                                editable: true,
                                toolbar: [
                                    {
                                        type: 'i',
                                        content: 'undo',
                                        onclick: function () {
                                            spreadsheet.undo();
                                        }
                                    }, {
                                        type: 'i',
                                        content: 'redo',
                                        onclick: function () {
                                            spreadsheet.redo();
                                        }
                                    }, {
                                        type: 'i',
                                        content: 'save',
                                        onclick: function () {
                                            spreadsheet.download();
                                        }
                                    }, {
                                        type: 'select',
                                        k: 'font-family',
                                        v: ['Arial', 'Verdana']
                                    }, {
                                        type: 'select',
                                        k: 'font-size',
                                        v: [
                                            '9px',
                                            '10px',
                                            '11px',
                                            '12px',
                                            '13px',
                                            '14px',
                                            '15px',
                                            '16px',
                                            '17px',
                                            '18px',
                                            '19px',
                                            '20px'
                                        ]
                                    }, {
                                        type: 'i',
                                        content: 'format_align_left',
                                        k: 'text-align',
                                        v: 'left'
                                    }, {
                                        type: 'i',
                                        content: 'format_align_center',
                                        k: 'text-align',
                                        v: 'center'
                                    }, {
                                        type: 'i',
                                        content: 'format_align_right',
                                        k: 'text-align',
                                        v: 'right'
                                    }, {
                                        type: 'i',
                                        content: 'format_bold',
                                        k: 'font-weight',
                                        v: 'bold'
                                    }, {
                                        type: 'color',
                                        content: 'format_color_text',
                                        k: 'color'
                                    }, {
                                        type: 'color',
                                        content: 'format_color_fill',
                                        k: 'background-color'
                                    }
                                ]
                            });
                            Object.assign(this, spreadsheet);
                        },
                    });

                    // var selectedProbTable = new Vue({     el: '#selectedProbTable',     mounted:
                    // function () {         let spreadsheet = jspreadsheet(this.$el, {
                    // minDimensions: [                 30, 30             ],
                    // defaultColWidth: 100,             tableOverflow: true,
                    // tableWidth: "100%",             csv: '/assets/selection/completegacha.csv',
                    // csvHeaders: false,             editable: true,             toolbar: [
                    // {                     type: 'i',                     content: 'undo',
                    // onclick: function () {                         spreadsheet.undo();
                    // }                 }, {                     type: 'i',
                    // content: 'redo',                     onclick: function () {
                    // spreadsheet.redo();                     }                 }, {
                    // type: 'i',                     content: 'save',                     onclick:
                    // function () {                         spreadsheet.download();
                    // }                 }, {                     type: 'select',
                    // k: 'font-family',                     v: ['Arial', 'Verdana']
                    // }, {                     type: 'select',                     k: 'font-size',
                    // v: [                         '9px',                         '10px',
                    // '11px',                         '12px',                         '13px',
                    // '14px',                         '15px',                         '16px',
                    // '17px',                         '18px',                         '19px',
                    // '20px'                     ]                 }, {                     type:
                    // 'i',                     content: 'format_align_left',                     k:
                    // 'text-align',                     v: 'left'                 }, {
                    // type: 'i',                     content: 'format_align_center',
                    // k: 'text-align',                     v: 'center'                 }, {
                    // type: 'i',                     content: 'format_align_right',
                    // k: 'text-align',                     v: 'right'                 }, {
                    // type: 'i',                     content: 'format_bold',                     k:
                    // 'font-weight',                     v: 'bold'                 }, {
                    // type: 'color',                     content: 'format_color_text',
                    // k: 'color'                 }, {                     type: 'color',
                    // content: 'format_color_fill',                     k: 'background-color'
                    // }             ]         });         Object.assign(this, spreadsheet);     }
                    // });
                }
            }
        });
    }
}

gacha.init();
