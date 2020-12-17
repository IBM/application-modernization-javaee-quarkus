module.exports = {
  lintOnSave: false,
  configureWebpack: {
    devServer: {
      headers: {
        "Access-Control-Allow-Origin": "*",
      },
      disableHostCheck: true,
      sockPort: 8504,
      sockHost: "localhost",
      https: false,
      port: 8504,
    },
    externals: ["vue", "vue-router", /^@vue-app-mod\/.+/],
  },
  filenameHashing: false,
};
