const gacha = {
    init: function () {
        const self = this;
        self.initModal();
        self.initUI();
    },
    initModal: function () {
        // register modal component
        Vue.component("modal", {template: "#modal-template"});

        // start app
        new Vue({
            el: "#app",
            data: {
                showModal: false
            }
        });
    },
    initUI: function () {
        const self = this;
        const mainTableName = '#layoutSidenav_content';
        const mainTable = new Vue({
            el: mainTableName,
            data: function () {
                return {
                    table: {
                        crawledProbTable: '',
                        userProbTable: ''
                    },
                    crawled: {
                        display: false
                    },
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
                    this.table.crawledProbTable = new Vue({
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
                    this.table.userProbTable = new Vue({
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
                        }
                    });
                },
                ToggleCrawled: function () {
                    this.crawled.display = !this.crawled.display;
                },
                Calc: function () {
                    console.log('Calc');

                    // 게임 이름과 아이템 유효성검사 switch 조건문
                    let selectedGacha = this.gacha.selected;
                    switch (selectedGacha) {
                        // 컴플리트가챠
                        case '1':
                            {
                                // 입력 가져오기
                                let itemCnt = this
                                    .table
                                    .userProbTable
                                    .getColumnData(1)
                                    .filter(function (el) {
                                        return 0 < el.length;
                                    });
                                let itemNames = this
                                    .table
                                    .userProbTable
                                    .getColumnData(2)
                                    .filter(function (el) {
                                        return 0 < el.length;
                                    });
                                let itemProbs = this
                                    .table
                                    .userProbTable
                                    .getColumnData(3)
                                    .filter(function (el) {
                                        return 0 < el.length;
                                    });

                                console.log(itemCnt);
                                console.log(itemNames);
                                console.log(itemProbs);

                                // 입력 유효성 검사
                                if (itemCnt.length != 2) {
                                    alert('아이템 개수를 한개만 입력해주세요');
                                    return;
                                } else {
                                    itemCnt = itemCnt[1];
                                }
                                if (itemNames.length - 1 != itemCnt) {
                                    alert('아이템 개수와 아이템 이름의 개수가 맞지 않습니다.');
                                    return;
                                } else {
                                    itemNames = itemNames.slice(1);
                                }
                                if (itemProbs.length -1 != itemCnt) {
                                    alert('아이템 개수와 아이템 확률의 개수가 맞지 않습니다.');
                                    return;
                                } else {
                                    itemProbs = itemProbs.slice(1);
                                }

                                // 서버로 요청
                                const data = {
                                    // gamename: gamename,
                                    // itemname: itemname,
                                    // tryprice: tryprice,
                                    itemCnt: itemCnt,
                                    itemNames: itemNames,
                                    itemProbs: itemProbs
                                };

                                $
                                    .ajax({
                                        type: 'POST',
                                        url: '/api/calc/completegacha',
                                        //dataType: 'JSON',
                                        contentType: 'application/json; charset=utf-8',
                                        data: JSON.stringify(data),
                                        success: function (retval) {
                                            console.log(retval);
                                        }
                                    })
                                    .done(function () {})
                                    .fail(function (error) {
                                        alert(JSON.stringify(error));
                                    });
                            }
                            break;
                            // 다른 아이템 구간별 확률
                        case '2':
                            {
                                console.log("900원");
                            }
                            break;
                            // 동일 아이템 구간별 확률
                        case '3':
                            {
                                console.log("700원");
                            }
                            break;
                            // 천장이 있는 복원추출
                        case '4':
                            {
                                console.log("700원");
                            }
                            break;
                            // 복원추출
                        case '5':
                            {
                                console.log("700원");
                            }
                            break;
                    }
                    // 시트에서 확률 정보 가져옴 ajax로 서버로 정보 전달 후, 계산 결과 받아옴 받아온 계산 결과 바탕으로 차트 그림
                }
            }
        });
    }
}

gacha.init();
