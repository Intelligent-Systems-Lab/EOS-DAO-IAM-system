cmake_minimum_required(VERSION 3.12)
project(ipfs VERSION 1.0.0)
set(EXECUTABLE_OUTPUT_PATH ${CMAKE_CURRENT_SOURCE_DIR})
find_package(eosio.cdt)
add_contract( ipfs ipfs_test main.cpp )