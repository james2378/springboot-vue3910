<template>
    <div class="form_address">
        <div class="header">修改收货地址</div>
        <div class="warp">
            <div class="container">
                <div class="row justify-content-between">
                    <div class="col-3">
                        <div class="card_menu">
                            <div class="name">
                                <span>收货人</span>
                                <input type="text" v-model="data.name"/>
                            </div>
                            <div class="phone">
                                <span>手机号</span>
                                <input type="text" v-model="data.phone"/>
                            </div>
                            <div class="address">
                                <span>详细地址</span>
                                <b-form-textarea
                                        id="textarea"
                                        v-model="data.address"
                                        placeholder="请输入地址"
                                        rows="3"
                                        max-rows="6"
                                ></b-form-textarea>
                            </div>
                            <button class="btn_save" type="default" @click="save_address()">
                                保存
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import mixin from "@/mixins/page.js";

    export default {
        mixins: [mixin],
        props: {
            data: {
                type: Object,
                default: {},
            },
            // vm: {
            //     type: Object,
            //     default: function () {
            //         return {
            //             address: "address",
            //             address_id: "address_id",
            //             name: "name",
            //             phone: "phone",
            //         };
            //     },
            // },
        },
        methods: {
            /**
             * 保存地址
             */
            save_address() {
                var data = this.data;
                var {address, name, phone, address_id, user_id} = data;
                user_id = this.user.user_id;
                if (address_id) {
                    this.$post(
                        `~/api/address/set?address_id=${address_id}`,
                        {address, name, phone, user_id},
                        (res) => {
                            // console.log(res);
                            this.$router.push("./address");
                        }
                    );
                } else {
                    this.$get('~/api/address/count?', {
                        user_id
                    }, (res) => {
                        // console.log(res.result)
                        if (res.result === 0) {
                            this.$post(
                                `~/api/address/add?`,
                                {address, name, phone, default: 1, user_id},
                                (res) => {
                                    console.log(res);
                                    this.$router.push("./address");
                                }
                            );
                        }else {
                          this.$post(
                                  `~/api/address/add?`,
                                  {address, name, phone, user_id},
                                  (res) => {
                                    // console.log(res);
                                    this.$router.push("./address");
                                  }
                          );
                        }
                    });
                }
            },
        },
        mounted() {
        },
    };
</script>

<style scoped>
    .form_address {
        padding: 0 0.8rem;
        min-height: 580px;
    }

    .header {
        justify-content: center;
        text-align: center;
    }

    .form_address input {
        display: inline-block;
    }

    .form_address > div {
        display: flex;
        align-items: center;
        padding: 0.5rem 0;
        border-bottom: 1px solid #f4f4f4;
        font-size: 0.9rem;
    }

    .form_address > div > text {
        width: 4.5rem;
    }

    .form_address > div > input {
        font-size: 0.9rem;
    }

    .form_address > div > textarea {
        font-size: 0.9rem;
    }

    .address {
        align-items: baseline;
    }

    .form_address .address > text {
        width: 5.5rem;
        margin-right: 0.5rem;
    }

    .btn_save {
        margin-top: 0.5rem;
        color: var(--color_primary);
    }
</style>
