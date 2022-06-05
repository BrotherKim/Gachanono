const gacha = {

    init: function () {
        const _this = this;
        _this.createSpreadsheet();
        // 게시글 저장
        $('#game').change(function () {
            console.log('changed!')
            console.log($('#game').val());
            //_this.save();
        });
        
    },
    createSpreadsheet: function () {
        console.log('createSpreadsheet start');
        var itemInfoTable = new Vue({
            el: '#itemInfoTable',
            data: function() {
                return {
                    game: {
                        selected: ''
                    },
                    item: {
                        list: [
                            {id: '1', itemname: 'aaa'},
                            {id: '2', itemname: 'bbb'},
                            {id: '3', itemname: 'ccc'},
                            {id: '4', itemname: 'ddd'},
                        ],
                        selected: ''
                    },
                    gacha: {
                        selected: ''
                    },
                    message: 'Hello Vue!'
                };
            },
            mounted: function () {
            },
            methods: {
                GetItems: function () {
                    return this.items;
                },
                GameSelected: function () {
                    console.log(this.game.selected);
                    //return this.items;
                },
            },
        });

        var probInfoTable = new Vue({
            el: '#probInfoTable',
            data: function() {
                return {
                    game: {
                        selected: ''
                    },
                    item: {
                        list: [
                            {id: '1', itemname: 'aaa'},
                            {id: '2', itemname: 'bbb'},
                            {id: '3', itemname: 'ccc'},
                            {id: '4', itemname: 'ddd'},
                        ],
                        selected: ''
                    },
                    gacha: {
                        selected: ''
                    },
                    message: 'Hello Vue!'
                };
            },
            mounted: function () {
            },
            methods: {
                GetItems: function () {
                    return this.items;
                },
                GameSelected: function () {
                    console.log(this.game.selected);
                    //return this.items;
                },
            },
        });

        var crawledProbTable = new Vue({
            el: '#crawledProbTable',
            mounted: function () {
                let spreadsheet = jspreadsheet(this.$el, {
                    minDimensions: [
                        30, 30
                    ],
                    defaultColWidth: 100,
                    tableOverflow: true,
                    tableWidth: "100%",
                    csv: '/assets/crawled/kartriderrushplus.csv',
                    csvHeaders: false,
                    editable: false
                });
                Object.assign(this, spreadsheet);
            }
        });

        var selectedProbTable = new Vue({
            el: '#selectedProbTable',
            mounted: function () {
                let spreadsheet = jspreadsheet(this.$el, {
                    minDimensions: [
                        30, 30
                    ],
                    defaultColWidth: 100,
                    tableOverflow: true,
                    tableWidth: "100%",
                    csv: '/assets/selection/completegacha.csv',
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
            }
        });
    },
}

gacha.init();
