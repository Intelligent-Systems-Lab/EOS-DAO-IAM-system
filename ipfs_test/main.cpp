#include <eosio/eosio.hpp>
using namespace eosio;
class [[eosio::contract]] ipfs : public contract {
    public:
        using contract::contract;
        [[eosio::action]] void upload(){
            print("Hello ",get_self());
            print("Hello ",get_first_receiver());
        }
    };
EOSIO_DISPATCH( ipfs, (upload) )