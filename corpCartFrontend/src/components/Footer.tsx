
const Footer = () =>  {
    
    return <>
        <footer className="bg-gradient-to-br from-gray-900 to-indigo-900 text-white relative overflow-hidden">
        <div className="absolute inset-0">
          <div className="absolute top-10 right-10 w-64 h-64 bg-purple-500/10 rounded-full blur-3xl animate-pulse"></div>
        </div>
        
        <div className="relative max-w-7xl mx-auto px-4 py-20">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-12">
            <div className="lg:col-span-2">
              <div className="flex items-center space-x-4 mb-8">
                <div>
                  <div className="text-3xl font-black">NIDO-TECHNOLOGY</div>
                  <div className="text-lg text-purple-300">Premium E-Commerce Excellence</div>
                </div>
              </div>
              <p className="text-gray-300 text-lg mb-8">
                Experience the future of online shopping with our cutting-edge platform and exceptional service.
              </p>
              
              <div className='flex space-x-4'>
                {[
                  { letter: 'f', color: '#1877F2', name:'Facebook'},
                  { letter: 't', color: '#1DA1F2', name:'Twitter'},
                  { letter: 'in', color: '#E4405F', name:'Instagram'},
                  { letter: 'L', color: '#0A66C2', name:'LinkedIn'},

      ].map((social, i) => (
        <a
          key={i}
          href="#"
          className="group relative w-14 h-14 bg-gray-900 border-2 border-gray-700 rounded-2xl flex items-center justify-center transition-all duration-300 hover:scale-110 hover:border-current"
          style={{
            color: social.color,
            // @ts-ignore
            '--glow-color': social.color
          }}
          onMouseEnter={(e) => {
            (e.currentTarget as HTMLElement).style.boxShadow = `0 0 30px ${social.color}50, inset 0 0 20px ${social.color}20`;
          }}
          onMouseLeave={(e) => {
            (e.currentTarget as HTMLElement).style.boxShadow = 'none';
          }}
        >
          <span className="font-bold text-lg transition-all duration-300 group-hover:drop-shadow-lg">
            {social.letter}
          </span>
        </a>
      ))}
    </div>
              </div>
            
            <div>
              <h3 className="text-2xl font-bold mb-8">Quick Links</h3>
              <ul className="space-y-4">
                {['About Us', 'Products', 'Services', 'Contact', 'Blog'].map((link) => (
                  <li key={link}>
                    <a href="#" className="text-gray-300 hover:text-white text-lg transition-all duration-300">
                      {link}
                    </a>
                  </li>
                ))}
              </ul>
            </div>
            
            <div>
              <h3 className="text-2xl font-bold mb-8">Contact Info</h3>
              <div className="space-y-6">
                <div className="text-gray-300">
                  <div className="text-lg mb-2">📍 Location</div>
                  <div>xyz</div>
                </div>
                <div className="text-gray-300">
                  <div className="text-lg mb-2">📞 Phone</div>
                  <div>+1 (555) 123-4567</div>
                </div>
                <div className="text-gray-300">
                  <div className="text-lg mb-2">✉️ Email</div>
                  <div>info@nido-technology.com</div>
                </div>
              </div>
            </div>
          </div>
          
          <div className="border-t border-white/20 mt-16 pt-12 flex flex-col md:flex-row justify-between items-center">
            <div className="text-gray-300 text-lg mb-4 md:mb-0">
              © 2025 NIDO-TECHNOLOGY. All rights reserved.
            </div>
            <div className="flex space-x-8">
              {['Privacy Policy', 'Terms of Service', 'Cookies'].map((item) => (
                <a key={item} href="#" className="text-gray-300 hover:text-white transition-colors duration-300">
                  {item}
                </a>
              ))}
            </div>
          </div>
        </div>
      </footer>
    </>
}

export default Footer;