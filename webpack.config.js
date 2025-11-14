const path = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

module.exports = {
  entry: {
    app: './room-web/src/main/webapp/app.js',
  },
  devtool: 'inline-source-map',
  devServer: {
    static: './target',
    hot: true,
  },
  plugins: [
    new MiniCssExtractPlugin({
      filename: '[name].css',
    }),
  ],
  module: {
     rules: [
       {
         test: /\.js$/,
         exclude: /node_modules/,
         use: {
           loader: 'babel-loader',
           options: {
             presets: ['@babel/preset-env'],
             plugins: [
               ['@babel/plugin-proposal-decorators', { legacy: true }],
               ['@babel/plugin-proposal-class-properties', { loose: true }],
             ],
           },
         },
       },
       {
         test: /\.css$/,
         use: ['style-loader', 'css-loader'],
       },
       {
         test: /\.s[ac]ss$/i,
         use: [
         MiniCssExtractPlugin.loader,
         "css-loader",
         "sass-loader",
         ],
       },
       // ... other asset loaders
     ],
   },
  output: {
    assetModuleFilename: 'images/[name][ext]',
    filename: '[name].bundle.js',
    path: path.resolve(__dirname, './room-web/src/main/webapp'),
    clean: false,
  },
};
